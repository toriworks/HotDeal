package tori.app.msholmes.activity;


import retrofit.RestAdapter;
import tori.app.msholmes.R;
import tori.app.msholmes.activity.guide.GuideActivity;
import tori.app.msholmes.network.UserService;
import tori.app.msholmes.utils.HCommonUtil;
import tori.app.msholmes.utils.HContants;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class LogInActivity extends Activity {

    /** 이메일 주소 입력 */
    private static EditText editEmail;
    /** 비밀번호 입력 */
    private static EditText editPasswd;
    /** 로그인 버튼 */
    private static Button buttonLogin;
    /** 페이스북 연동 버튼 */
    private static Button buttonSocialFacebook;
    /** 트위터 연동 버튼 */
    private static Button buttonSocialTwitter;
    /** 구글 플러스 연동 버튼 */
    private static Button buttonSocialGoogle;
    /** 소셜 로그인 안내 버튼 */
    private static Button buttonGuideLogin;
    /** 이메일 입력 안내 문구 */
    private static TextView textGuideEmail;
    /** 비밀번호 입력 안내 문구 */
    private static TextView textGuidePasswd;
    /** 안내 다이얼로그 */
    private ProgressDialog dialog;
    /** 메세지 핸들러 */
    private Handler mHandler;
    // Runnable that executes the background processing method.
    private Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() {
            backgroundThreadProcessing();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();

        // 다이얼로그 생성
        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려 주세요.");
        dialog.setCancelable(false);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int what = msg.what;
                switch (what) {
                    case 0:
                        // 다이얼로그 숨김
                        dialog.cancel();
                        break;
                    case 1:
                        // 다이얼로그 메세지 변경
                        dialog.setMessage("로그인 처리중입니다.");
                        break;
                    case 500:
                        // 기능오류로 인한 토스트 출력
                        Toast.makeText(getApplicationContext(), "서버와 연결이 원활하지 않습니다.\n잠시 후, 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
    }

    /**
     * 화면 컴포넌트를 초기화한다.
     */
    private void initScreenComponents() {
        // 전체 화면으로 보이기 위해서 액션바를 숨김
        final ActionBar bar = getActionBar();
        bar.hide();

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPasswd = (EditText) findViewById(R.id.edit_passwd);

        // 로그인 버튼
        buttonLogin = (Button) findViewById(R.id.button_log_in);
        buttonLogin.setOnClickListener(new HOnClickListener());

        // 소셜 로그인 연동 버튼
        buttonSocialFacebook = (Button) findViewById(R.id.button_social_facebook);
        buttonSocialFacebook.setOnClickListener(new HOnClickListener());
        buttonSocialTwitter = (Button) findViewById(R.id.button_social_twitter);
        buttonSocialTwitter.setOnClickListener(new HOnClickListener());
        buttonSocialGoogle = (Button) findViewById(R.id.button_social_google);
        buttonSocialGoogle.setOnClickListener(new HOnClickListener());

        // 소셜 로그인 안내 버튼
        buttonGuideLogin = (Button) findViewById(R.id.button_guide_login);
        buttonGuideLogin.setOnClickListener(new HOnClickListener());

        // 입력 안내 문구
        textGuideEmail = (TextView) findViewById(R.id.text_guide_email);
        textGuideEmail.setVisibility(View.GONE);
        textGuidePasswd = (TextView) findViewById(R.id.text_guide_passwd);
        textGuidePasswd.setVisibility(View.GONE);
    }

    /**
     * 키패드를 숨긴다.
     */
    private void hideSoftKeypad() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editPasswd.getWindowToken(), 0);
    }

    /**
     * 가이드로 보이는 내용을 숨긴다.
     */
    private void hideGuide() {
        textGuideEmail.setVisibility(View.GONE);
        textGuidePasswd.setVisibility(View.GONE);
    }

    // This method is called on the main GUI thread.
    private void backgroundExecution() {
        // This moves the time consuming operation to a child thread.
        Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
        thread.start();
    }

    private void backgroundThreadProcessing() {

        mHandler.sendEmptyMessage(1);

        // 통신 상에 오류가 있을 경우 대비
        try {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setServer(HContants.API_ENDPOINT)
                    .build();

            Log.d("holmes", "보내는값[" + editEmail.getText() + "," + editPasswd.getText() + "]");

            UserService service = restAdapter.create(UserService.class);
            JsonObject json = service.checkEmail(HCommonUtil.getStringWithSafe(editEmail.getText()),
                    HCommonUtil.getStringWithSafe(editPasswd.getText()));
            Log.d("holmes", "" + json.toString());
            Log.d("holmes", ">>" + json.get("result") + "<<");

            JsonElement resultObj = json.get("result");
            String result = resultObj.getAsString();

            if (HContants.SUCCESS.equals(result)) {
                // 응답이 성공인 경우
                JsonArray arrData = json.getAsJsonArray("data");
                JsonObject countObj = arrData.get(0).getAsJsonObject();

                JsonPrimitive countVal = countObj.getAsJsonPrimitive("count");

                Log.d("holmes", ">>countResult:" + arrData.toString() + ", " + countVal + "<<END");


                Intent mainIntent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
            } else {
                // 응답이 실패인 경우
                mHandler.sendEmptyMessage(500);
            }

        } catch (Exception e) {
            Log.e("holmes", ">>>>>" + e.toString());
            e.printStackTrace();
            mHandler.sendEmptyMessage(500);

            // FIXME : 강제로 다음 액티비티로 이동하게 함
            Intent mainIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
        }

        mHandler.sendEmptyMessage(0);
    }

    /**
     * 클릭 이벤트 처리를 담당하는 클래스
     *
     * @author toriworks
     */
    private class HOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int vId = v.getId();
            switch (vId) {
                case R.id.button_log_in:    // 로그인 이벤트 처리
                    // 입력값 체크
                    String mEmail = editEmail.getText().toString();
                    String mPasswd = editPasswd.getText().toString();
                    if (null == mEmail || "".equals(mEmail.trim())) {
                        textGuideEmail.setText("이메일을 입력해 주세요.");
                        textGuideEmail.setVisibility(View.VISIBLE);
                        return;
                    }

                    if (!HCommonUtil.isValidEmailAddress(mEmail)) {
                        textGuideEmail.setText("이메일 형식이 맞지 않습니다. ('@'와 '.' 포함)");
                        textGuideEmail.setVisibility(View.VISIBLE);
                        return;
                    }

                    textGuideEmail.setVisibility(View.GONE);

                    if (null == mPasswd || "".equals(mPasswd.trim())) {
                        textGuidePasswd.setText("비밀번호를 입력해 주세요.");
                        textGuidePasswd.setVisibility(View.VISIBLE);
                        editPasswd.requestFocus();
                        return;
                    }

                    if (mPasswd.trim().length() < 6) {
                        textGuidePasswd.setText("비밀번호는 최소 6자 이상입니다.");
                        textGuidePasswd.setVisibility(View.VISIBLE);
                        editPasswd.requestFocus();
                        return;
                    }

                    // 로그인 시도
                    // 기존에 가이드 된 부분은 숨김
                    hideGuide();

                    // 로그인 버튼이 눌리면 키패드 숨김
                    hideSoftKeypad();

                    // 로그인 중임을 알리는 다이얼로그 보이기
                    dialog.show();

                    // 로그인 처리 시작
                    backgroundExecution();
                    break;
                case R.id.button_social_facebook:    // 페이스북 연동

                    break;
                case R.id.button_social_twitter:    // 트위터 연동

                    break;
                case R.id.button_social_google:        // 구글플러스 연동

                    break;
                case R.id.button_guide_login:        // 소셜 로그인 안내
                    Intent guideIntent = new Intent(LogInActivity.this, GuideActivity.class);
                    startActivity(guideIntent);
				break;
			default:
				break;
			}
		}
	}
}
