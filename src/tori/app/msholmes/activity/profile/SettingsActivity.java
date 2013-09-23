package tori.app.msholmes.activity.profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.*;
import android.widget.*;
import com.androidquery.AQuery;
import tori.app.msholmes.R;
import tori.app.msholmes.activity.guide.GuideActivity;
import tori.app.msholmes.utils.HContants;

/**
 * 설정 화면 액티비티
 * <p/>설정 화면에서는 총 5가지의 설정 메뉴를 제공합니다.
 * <br/><ul><li>알림설정</li><li>공지사항</li><li>도움말</li><li>버전정보</li><li>계정</li></ul>
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class SettingsActivity extends Activity {

    /** 패키지 매니저에서 얻은 버전코드 */
    private int gVersionCode = -1;

    /** 현재 설치된 앱의 버전 */
    private static TextView textNowVersion;
    /** 업그레이드가 있다면 보여지는 업그레이드 표시 레이아웃 */
    private static RelativeLayout relativeUpgradeAvailable;
    /** 최신 앱의 버전 */
    private static TextView textUpgradeVesion;
    /** 구글 플레이 링크 이미지 */
    private static ImageView imageGoogleLink;
    /** 계정정보 레이아웃 */
    private static RelativeLayout relativeAccountLink;
    /** 계정정보 이동 버튼 */
    private static Button buttonAccountLink;
    /** 알림상세설정 레이아웃 */
    private static RelativeLayout relativeNotiLink;
    /** 알림상세설정 이동 버튼 */
    private static Button buttonNotiLink;
    /** 앱 둘러보기 레이아웃 */
    private static RelativeLayout relativeGuideLink;
    /** 앱 둘러보기 이동 버튼 */
    private static Button buttonGuideLink;
    /** 버전 확인 중 레이아웃 */
    private static RelativeLayout relativeUpgradeCheck;
    /** 팝업 메뉴 */
    private PopupMenu popupMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 액션바 초기화 설정
        this.initActionBar();

        // 뷰 컴포넌트 초기화 설정
        this.initViewComponents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
    }

    /**
     * 액션바를 초기화 설정한다.
     */
    private void initActionBar() {
        // 액션바 생성 후, 텍스트 타이틀 제거
        ActionBar bar = getActionBar();
        bar.setTitle("설정");

        bar.setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP,
                ActionBar.DISPLAY_HOME_AS_UP);
        bar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 화면의 뷰 컴포넌트들을 초기화한다.
     */
    private void initViewComponents() {
        // 레이아웃 초기화
        relativeUpgradeAvailable = (RelativeLayout) findViewById(R.id.relative_upgrade_available);
        relativeAccountLink = (RelativeLayout) findViewById(R.id.relative_account_link);
        relativeNotiLink = (RelativeLayout) findViewById(R.id.relative_noti_link);
        relativeGuideLink = (RelativeLayout) findViewById(R.id.relative_guide_link);
        relativeUpgradeCheck = (RelativeLayout) findViewById(R.id.relative_upgrade_check);

        // 버튼 초기화
        imageGoogleLink = (ImageView) findViewById(R.id.image_google_link);
        buttonAccountLink = (Button) findViewById(R.id.button_account_link);
        buttonNotiLink = (Button) findViewById(R.id.button_noti_link);
        buttonGuideLink = (Button) findViewById(R.id.button_guide_link);

        // 버튼 클릭 이벤트
        imageGoogleLink.setOnClickListener(new ButtonOnClickListener());
        buttonAccountLink.setOnClickListener(new ButtonOnClickListener());
        buttonNotiLink.setOnClickListener(new ButtonOnClickListener());
        buttonGuideLink.setOnClickListener(new ButtonOnClickListener());

        // 레이아웃 클릭 이벤트
        relativeAccountLink.setOnClickListener(new ButtonOnClickListener());
        relativeNotiLink.setOnClickListener(new ButtonOnClickListener());
        relativeGuideLink.setOnClickListener(new ButtonOnClickListener());

        // 앱버전 관련 표시 초기화
        textNowVersion = (TextView) findViewById(R.id.text_now_version);
        this.getShowAppVersion();

        // 업그레이드 관련 표시 초기화
        textUpgradeVesion = (TextView) findViewById(R.id.text_upgrade_version);
        this.getUpgradeVersion();

        // 계정정보와 연결된 POPUPMENU 생성
        this.initPopupMenu();

    }

    /**
     * 계정정보 링크와 연결된 팝업메뉴를 생성한다.
     */
    private void initPopupMenu() {
        Log.d("nine", "팝업메뉴 초기화");
        if(null == popupMenu) {
            Log.d("nine", "팝업메뉴 생성");
            popupMenu = new PopupMenu(SettingsActivity.this, buttonAccountLink);
            popupMenu.inflate(R.menu.activity_settings);
        }

        // 팝업 메뉴에 이벤트 추가
        Log.d("nine", "팝업메뉴 이벤트추가");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /**
             * This method will be invoked when a menu item is clicked if the item itself did
             * not already handle the event.
             *
             * @param item {@link android.view.MenuItem} that was clicked
             * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
             */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int mId = item.getItemId();
                switch (mId) {
                    case R.id.menu_settings_logout:
                        Log.d("nine", "로그아웃 선택");
                        break;
                    case R.id.menu_settings_contract:
                        Log.d("nine", "이용약관 선택");
                        break;
                    case R.id.menu_settings_act:
                        Log.d("nine", "개인정보보호정책 선택");
                        break;
                    case R.id.menu_settings_out:
                        Log.d("nine", "탈퇴신청 선택");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 서버에서 최신의 앱이 있는지 버전번호를 얻어와서,
     * 현재 앱의 버전코드와 비교하여 업그레이드 레이아웃을 보여줄지말지를 결정한다.
     */
    private void getUpgradeVersion() {
        // TODO : 서버에 저장된 버전코드를 얻어와서 현재 앱과 비교

        // 버전 확인 중 레이아웃을 숨김
        relativeUpgradeCheck.setVisibility(View.GONE);

        int serverAppVersionCode = 2;
        if(gVersionCode < serverAppVersionCode) {
            // 서버 버전이 높으므로 업그레이드 레이아웃 노출
            relativeUpgradeAvailable.setVisibility(View.VISIBLE);
        } else {
            relativeUpgradeAvailable.setVisibility(View.GONE);
        }
    }

    /**
     * 현재 앱의 버전을 얻어서 화면에 표시한다.
     * <p/>앱 버전은 패키지매니저에서 문자열로 얻어와서 표시합니다.
     */
    private void getShowAppVersion() {
        String version = "^00.00.00";
        try {
            PackageInfo i = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = i.versionName;
            // 버전 코드도 함께 추출
            gVersionCode = i.versionCode;

            textNowVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    /**
     * 옵션 메뉴가 선택되면 이벤트가 호출된다.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mId = item.getItemId();
        Log.d("nine", "선택된 메뉴 아이디:" + mId + "<END");
        switch (mId) {
            case android.R.id.home:
                Log.d("nine", "홈 버튼이 눌림");
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                case R.id.image_google_link:
                    // 구글 플레이로 이동
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(HContants.GOOGLE_PLAY_LINK));
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.relative_account_link:
                case R.id.button_account_link:
                    // 계정정보로 이동
                    Log.d("nine", "계정정보 하위메뉴 POPUP MENU");
                    if(null != popupMenu) {
                        Log.d("nine", "팝업메뉴 보이기");
                        popupMenu.dismiss();
                        popupMenu.show();
                    } else {
                        Log.d("nine", "팝업메뉴 null");
                    }
                    break;
                case R.id.relative_noti_link:
                case R.id.button_noti_link:
                    // 알림상세설정으로 이동
                    Log.d("nine", "알림상세설정으로 이동");
                    break;
                case R.id.relative_guide_link:
                case R.id.button_guide_link:
                    // 앱 둘러보기 POPUP
                    Log.d("nine", "앱 둘러보기 POPUP");
                    Intent guideIntent = new Intent(SettingsActivity.this, GuideActivity.class);
                    startActivity(guideIntent);
                    break;
                default:
                    break;
            }
        }
    }
}