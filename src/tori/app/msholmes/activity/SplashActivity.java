package tori.app.msholmes.activity;

import tori.app.msholmes.R;
import tori.app.msholmes.utils.HContants;
import tori.app.msholmes.utils.HSharedPreference;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {

	/** 회원가입 버튼 */
	private static Button buttonSignUp;
	/** 로그인 버튼 */
	private static Button buttonLogIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// 화면 컴포넌트 초기화
		this.initScreenComponents();
		
		// 자동로그인여부 확인 
		this.checkAutoLoginActivate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 화면 컴포넌트를 초기화한다.
	 */
	private void initScreenComponents() {

		// 회원가입버튼
		buttonSignUp = (Button) findViewById(R.id.button_sign_up);
		buttonSignUp.setOnClickListener(new HOnClickListener());

		// 로그인버튼
		buttonLogIn = (Button) findViewById(R.id.button_log_in);
		buttonLogIn.setOnClickListener(new HOnClickListener());
		
		// 초기에 버튼은 숨기고, 자동로그인 결과에 따라서 보이게 함
		buttonSignUp.setVisibility(View.GONE);
		buttonLogIn.setVisibility(View.GONE);
	}
	
	/**
	 * 자동로그인 여부를 확인한다. 
	 */
	private void checkAutoLoginActivate() {
		String mAutoLogin = HSharedPreference.getValue(getApplicationContext(), HContants.SP_AUTO_LOGIN);
		if(null == mAutoLogin || "".equals(mAutoLogin)) {
			// 자동로그인 관련 저장값이 없는 경우 
			buttonSignUp.setVisibility(View.VISIBLE);
			buttonLogIn.setVisibility(View.VISIBLE);
		} else {
			if(!HContants.YES.equals(mAutoLogin)) {
				// 자동로그인 관련 저장값이 있으나, YES가 아닌 경우 
				buttonSignUp.setVisibility(View.VISIBLE);
				buttonLogIn.setVisibility(View.VISIBLE);
			} else {
				// 자동로그인으로 다음 액티비티로 이동 
				Intent nextIntent = new Intent(SplashActivity.this, SplashActivity.class);
				startActivity(nextIntent);
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
			}
		}
	}

	/**
	 * 클릭 이벤트 처리를 담당하는 클래스 
	 * @author toriworks
	 *
	 */
	private class HOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int vId = v.getId();
			switch (vId) {
			case R.id.button_sign_up:	// 회원가입 이벤트 처리 
				Intent signUpIntent = new Intent(SplashActivity.this, SignUpActivity.class);
				startActivity(signUpIntent);
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
				break;
			case R.id.button_log_in:	// 로그인 이벤트 처리
				Intent logInIntent = new Intent(SplashActivity.this, LogInActivity.class);
				startActivity(logInIntent);
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
				break;
			default:
				break;
			}
		}
	}

}
