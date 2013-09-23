package tori.app.msholmes.activity.feed;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import tori.app.msholmes.R;
import tori.app.msholmes.utils.HCallback;
import tori.app.msholmes.utils.HCommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kimhyoseok
 * Date: 13. 7. 26
 * Time: 오후 6:05
 * To change this template use File | Settings | File Templates.
 */
public class WriteActivity extends Activity {

    /** 글 작성 확인 버튼 */
    private static Button buttonConfirm;
    /** 글 제목 */
    private static EditText editTitle;
    /** 글 내용 */
    private static EditText editContents;
    /** 링크 주소 */
    private static EditText editLink;
    /** 태그 */
//    private static EditText editTags;
    /** 글 제목 미입력시 보일 뷰 */
    private static TextView textToastTitle;
    /** 글 내용 미입력시 보일 뷰 */
    private static TextView textToastContents;
    /** 백그라운드 레이아웃 */
    private static LinearLayout linearWriteBg;
    /** 구분용 spinner */
    private static Spinner spinnerGubun;

    /** Spinner 데이터 */
    private ArrayList<String> arrSpinnerGubun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // 액션바 초기화 설정
        this.initActionBar();

        // 화면 컴포넌트 초기화
        this.initScreenComponents();
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

        // 커스터마이징 액션바 사용
        bar.setCustomView(R.layout.g_actionbar_write);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

        // 모드 설정
        bar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 화면 컴포넌트를 초기화한다.
     */
    private void initScreenComponents() {
        // 확인 버튼
        buttonConfirm = (Button) findViewById(R.id.button_confirm);

        // 입력창들
        editTitle = (EditText) findViewById(R.id.edit_title);
        editContents = (EditText) findViewById(R.id.edit_contents);
        editLink = (EditText) findViewById(R.id.edit_link);
//        editTags = (EditText) findViewById(R.id.edit_tags);

        // 초기 생성시에 해당 카테고리의 이름을 태그에 삽입
//        editTags.setText("국내핫딜 ");

        // 미입력시 나오는 뷰
        textToastTitle = (TextView) findViewById(R.id.text_toast_title);
        textToastContents = (TextView) findViewById(R.id.text_toast_contents);

        // 백그라운드 레이아웃
        linearWriteBg = (LinearLayout) findViewById(R.id.linear_write_bg);
        linearWriteBg.setOnClickListener(new ButtonOnClickListener());

        // 구분선택용 spinner
        spinnerGubun = (Spinner) findViewById(R.id.spinner_gubun);
        // 구분에 사용할 값을 spinner에 할당
        initSpinnerGubun();

        // 버튼 이벤트 처리
        buttonConfirm.setOnClickListener(new ButtonOnClickListener());
    }

    /**
     * 구분에 사용할 값을 얻어서, Spinner에 할당한다.
     */
    private void initSpinnerGubun() {
        String[] strArrayMenus = getResources().getStringArray(R.array.write_gubun_menu);
        arrSpinnerGubun = new ArrayList<String>();
        for(int i=0; i<strArrayMenus.length; i++) {
            arrSpinnerGubun.add(strArrayMenus[i]);
        }

        // Normal 한 모양을 만들려면 setDropDownViewResolver를 따로 설정
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrSpinnerGubun);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGubun.setAdapter(adapter);
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
     * 태그에 대해서 띄어쓰기를 쉼표로 변환한다.
     */
//    private void handleTags(String strTags) {
//        strTags = strTags.replace(',', ' ');
//        editTags.setText(strTags.trim());
//    }

    private void hideSoftKeypad() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
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
                case R.id.button_confirm:
                    // 글 확인 버튼 클릭
                    String strTitle = HCommonUtil.getStringWithSafe(editTitle.getText().toString());
                    String strContents = HCommonUtil.getStringWithSafe(editContents.getText().toString());
                    String strLink = HCommonUtil.getStringWithSafe(editLink.getText().toString());
//                    String strTags = HCommonUtil.getStringWithSafe(editTags.getText().toString());

                    // 미입력 항목에 대한 선처리
                    textToastTitle.setVisibility(View.GONE);
                    textToastContents.setVisibility(View.GONE);

                    if(strTitle.length() <= 0) {
                        // 글 제목이 입력되지 않음
                        Log.d("nine", ">>>글 제목이 입력되지 않음");

                        // TODO : Toast 로 안내 메세지 띄우고
                        textToastTitle.setVisibility(View.VISIBLE);
                        editTitle.setFocusable(true);
                        return;
                    }

                    if(strContents.length() <= 0 ) {
                        // 글 내용이 입력되지 않음
                        Log.d("nine", ">>>글 내용이 입력되지 않음");

                        // TODO : Toast 로 안내 메세지 띄우고
                        textToastContents.setVisibility(View.VISIBLE);
                        editContents.setFocusable(true);
                        return;
                    }

                    // 태그에 대한 처리 마무리
//                    handleTags(strTags);

                    // 모든 validation 처리가 완료되면 서버로 내용을 전송
                    Log.d("nine", ">>>서버로 내용을 전송");

                    break;
                case R.id.linear_write_bg:
                    // 레이아웃의 백그라운드 클릭시에는 키패드 내려가기
                    hideSoftKeypad();
                    break;
                default:
                    break;
            }
        }
    }
}
