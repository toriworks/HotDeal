package tori.app.msholmes.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import tori.app.msholmes.R;
import tori.app.msholmes.activity.feed.WriteActivity;
import tori.app.msholmes.activity.fragment.AbroadFragment;
import tori.app.msholmes.activity.fragment.DomesticFragment;
import tori.app.msholmes.activity.fragment.MoreFragment;
import tori.app.msholmes.activity.fragment.QnAFragment;
import tori.app.msholmes.activity.guide.GuideActivity;
import tori.app.msholmes.activity.guide.HelpActivity;
import tori.app.msholmes.activity.profile.ProfileActivity;
import tori.app.msholmes.utils.HCallback;
import tori.app.msholmes.utils.listener.TabListener;

/**
 * 메인 피드 액티비티
 * <p/>
 * Created by toriworks on 13. 7. 17..
 */
public class MainActivity extends Activity implements HCallback {

    /** 알림 센터 */
    private static Button buttonNotice;
    /** 사진첨부 글쓰기 */
    private static Button buttonPic;
    /** 클리핑 글쓰기 */
    private static Button buttonClip;
    /** 글쓰기 */
    private static Button buttonWrite;
    /** 메뉴 보이기/숨기기 */
    private static Button buttonMenu;
    /** 알림 센터에 신규 데이터가 있을 경우에 사용할 drawable */
    private int noticeCenterDrawable = R.drawable.layer_customizer;
    /** 메뉴 숨겨진/보여진 drawable */
    private int[] menuShowHideDrawable = {
            R.drawable.ic_action_collections_view_as_grid,
            R.drawable.ic_action_collections_view_as_grid_p
    };
    /** 메뉴 보이기/숨기기 체크 값 */
    private static boolean bShowMenu = false;

    /** 백버튼 누름 감지용 체크 값 */
    private static boolean bWillClosed = false;
    /** 백버튼 누름 감지용 핸들러 */
    private static Handler willCloseHandler = new Handler() {
        /*
         * (non-Javadoc)
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            bWillClosed = false;
        }
    };
    /** 메세지 처리 핸들러 */
    private Handler mHandler;
    /** 메인 화면 탭 메뉴 */
    private String[] arrMainTabMenu;
    /** 메인 화면 탭 메뉴 아이콘 */
    private int[] arrMainTabMenuIcon = {
            R.drawable.ic_action_location_place,
            R.drawable.ic_action_location_web_site,
            R.drawable.ic_action_qa,
            R.drawable.ic_action_more
    };

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 탭용 문자열 초기화 설정
        arrMainTabMenu = getResources().getStringArray(R.array.main_tab_menu);

        // 액션바 초기화 설정
        this.initActionBar();

        // 뷰 컴포넌트 초기화 설정
        this.initViewComponents();

        // 뒤로 버튼에 대한 처리 이벤트 핸들러를 등록
        this.handleBackButtonPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        if (!bWillClosed) {
            // Toast message 출력
            mHandler.sendEmptyMessage(0);

            bWillClosed = true;
            willCloseHandler.sendEmptyMessageDelayed(
                    1000, // 핸들러 아이디
                    3000); // 메세지 전달 시간적 여유
        } else {
            super.onBackPressed();
            //
            // 일정시간 내에 두번 뒤로 누르기를 했기 때문에 종료 시도
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 액션바를 초기화 설정한다.
     */
    private void initActionBar() {
        // 액션바 생성 후, 텍스트 타이틀 제거
        ActionBar bar = getActionBar();
        bar.setTitle("");

        // 커스터마이징 액션바 사용
        bar.setCustomView(R.layout.g_actionbar);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

        // 모드 설정
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Fragment 설정
        this.initFragments(bar);
    }

    /**
     * 화면의 뷰 컴포넌트들을 초기화한다.
     */
    private void initViewComponents() {
        // 상단 액션바 내부 버튼 초기화
        buttonMenu = (Button) findViewById(R.id.button_menu);
        buttonWrite = (Button) findViewById(R.id.button_write);
        buttonClip = (Button) findViewById(R.id.button_clip);
        buttonPic = (Button) findViewById(R.id.button_pic);
        //
        // 메뉴 보기/숨기기 버튼을 제외하고 나머지 버튼은 숨김
        buttonWrite.setVisibility(View.GONE);
        buttonClip.setVisibility(View.GONE);
        buttonPic.setVisibility(View.GONE);

        // 알림센터 버튼 초기화
        buttonNotice = (Button) findViewById(R.id.button_notice);
        buttonNotice.setBackgroundResource(R.drawable.layer_customizer);

        // 버튼에 이벤트 부여
        buttonMenu.setOnClickListener(new ButtonOnClickListener());
        buttonWrite.setOnClickListener(new ButtonOnClickListener());
        buttonClip.setOnClickListener(new ButtonOnClickListener());
        buttonPic.setOnClickListener(new ButtonOnClickListener());
    }

    /**
     * 각 탭을 담당하는 Fragment를 설정한다.
     *
     * @param bar ActionBar 객체
     */
    private void initFragments(ActionBar bar) {
        // 국내 핫딜 Fragment 설정
        bar.addTab(
                bar.newTab()
                        .setIcon(arrMainTabMenuIcon[0])
                        .setTabListener(new TabListener<DomesticFragment>(this, "tab1", DomesticFragment.class, this)));

        // 해외 핫딜 Fragment 설정
        bar.addTab(
                bar.newTab()
                        .setIcon(arrMainTabMenuIcon[1])
                        .setTabListener(new TabListener<AbroadFragment>(this, "tab2", AbroadFragment.class, this)));

        // Q&A Fragment 설정
        bar.addTab(
                bar.newTab()
                        .setIcon(arrMainTabMenuIcon[2])
                        .setTabListener(new TabListener<QnAFragment>(this, "tab3", QnAFragment.class, this)));

        // More Fragement 설정
        bar.addTab(
                bar.newTab()
                        .setIcon(arrMainTabMenuIcon[3])
                        .setTabListener(new TabListener<MoreFragment>(this, "tab4", MoreFragment.class, this)));
    }

    /**
     * 뒤로 버튼을 연속으로 누를 경우에 이벤트를 처리한다.
     */
    private void handleBackButtonPressed() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int what = msg.what;
                switch (what) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 메뉴 보이기/숨기기 이벤트를 처리한다.
     */
    private void toggleMenu() {
        bShowMenu = !bShowMenu;
        if (bShowMenu) {
            // 보이기
            buttonWrite.setVisibility(View.VISIBLE);
            buttonClip.setVisibility(View.VISIBLE);
            buttonPic.setVisibility(View.VISIBLE);
        } else {
            // 숨기기
            buttonWrite.setVisibility(View.GONE);
            buttonClip.setVisibility(View.GONE);
            buttonPic.setVisibility(View.GONE);
        }

        // 메뉴 보이기/숨기기 버튼 모양을 다르게 처리
        buttonMenu.setBackgroundResource(menuShowHideDrawable[(bShowMenu) ? 1 : 0]);
    }

    @Override
    public void tabSelected(String tabName) {
        // 네번째 탭이 선택되면 상단의 메뉴를 숨김
        if(null != buttonMenu) {
            // TODO : ----------------------------------------


        }
    }

    /**
     * 화면내 버튼 역할을 수행하는 컴포넌트의 클릭 이벤트 처리 클래스
     */
    private class ButtonOnClickListener implements View.OnClickListener {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            switch (vId) {
                case R.id.button_menu:
                    // 메뉴 보이기/숨기기 토글
                    toggleMenu();
                    break;
                case R.id.button_write:
                    // 메뉴 숨기기
                    toggleMenu();

                    // 새 글작성 화면으로 이동
                    Intent writeIntent = new Intent(MainActivity.this, WriteActivity.class);
                    startActivity(writeIntent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.button_clip:
                    // 메뉴 숨기기
                    toggleMenu();

                    break;
                case R.id.button_pic:
                    // 메뉴 숨기기
                    toggleMenu();

                    break;
                default:
                    break;
            }
        }
    }
}