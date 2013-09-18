package tori.app.msholmes.activity.profile;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import tori.app.msholmes.R;

/**
 * 설정 화면 액티비티
 * <p/>설정 화면에서는 총 5가지의 설정 메뉴를 제공합니다.
 * <br/><ul><li>알림설정</li><li>공지사항</li><li>도움말</li><li>버전정보</li><li>계정</li></ul>
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 액션바 초기화 설정
        this.initActionBar();
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