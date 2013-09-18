package tori.app.msholmes.activity;

import tori.app.msholmes.R;
import tori.app.msholmes.R.layout;
import tori.app.msholmes.R.menu;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends Activity {

    /** 계정생성 버튼 */
    private static Button buttonEnroll;
    /** 개인정보보호정책 링크 */
    private static TextView textAct1;
    /** 이용약관 링크 */
    private static TextView textAct2;

    /** 이름 */
    private static EditText editName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_sign_up);

        // 강제로 키패드를 내리도록 함
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();


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

        // 계정생성 버튼
        buttonEnroll = (Button) findViewById(R.id.button_enroll);

        // 개인정보보호정책 및 이용약관 링크
        textAct1 = (TextView) findViewById(R.id.text_link_act1);
        textAct2 = (TextView) findViewById(R.id.text_link_act2);

        // 클릭 이벤트 처리
        buttonEnroll.setOnClickListener(new HOnClickListener());
        textAct1.setOnClickListener(new HOnClickListener());
        textAct2.setOnClickListener(new HOnClickListener());


        // 입력정보
        editName = (EditText) findViewById(R.id.edit_name);
    }

    /**
     * 클릭 이벤트를 처리하는 클래스
     */
    private class HOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int vId = view.getId();
            switch (vId) {
                case R.id.button_enroll:
                    // 계정생성 버튼을 클릭

                    break;
                case R.id.text_link_act1:
                    // 개인정보보호정책 링크를 클릭

                    break;
                case R.id.text_link_act2:
                    // 이용약관 링크를 클릭

                    break;
                default:
                    break;
            }
        }
    }
}
