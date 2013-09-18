package tori.app.msholmes.activity.profile;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import tori.app.msholmes.R;
import tori.app.msholmes.utils.data.AdapterNotificationData;

/**
 * 사용자 프로필 조회 액티비티
 * <p/>사용자 프로필 조회 및 Followers, Following, 설정, 이벤트를 조회할 수 있습니다.
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class ProfileActivity extends Activity {

	/** 알림센터 데이터 리스트뷰 */
	private static ListView listNotification;
	/** 알림센터 데이터 */
	private List<String> listNotificationData = new ArrayList<String>();
	/** 알림센터 데이터 리스트뷰와 데이터를 연결할 어뎁터 */
	private AdapterNotificationData<String> notificationAdapter;
	
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// 액션바 초기화 설정
		this.initActionBar();

		// 화면 컴포넌트 초기화
		this.initScreenComponents();
		
		// 어뎁터 준비 
		notificationAdapter = new AdapterNotificationData<String>(this,
				R.layout.list_notification_data, listNotificationData);
		
		// 더미 데이터 준비
		for(int i=0; i<50; i++) {
			listNotificationData.add("");
		}
		
		listNotification.setAdapter(notificationAdapter);
		notificationAdapter.notifyDataSetChanged();
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
        bar.setTitle("프로필");

        bar.setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP,
                ActionBar.DISPLAY_HOME_AS_UP);
        bar.setDisplayHomeAsUpEnabled(true);
    }
    
    /**
     * 화면 컴포넌트를 초기화한다. 
     */
    private void initScreenComponents() {
    	//listNotification = (ListView) findViewById(R.id.list_notification);
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
}
