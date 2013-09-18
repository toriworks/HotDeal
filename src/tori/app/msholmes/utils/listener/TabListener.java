package tori.app.msholmes.utils.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: kimhyoseok
 * Date: 13. 7. 23
 * Time: 오후 3:13
 * To change this template use File | Settings | File Templates.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
    private Fragment mFragment;

    /**
     * 생성자
     *
     * @param activity 액티비티
     * @param tag      문자열 탭의 태그명
     * @param clz      클래스
     */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;

        mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
        if (mFragment != null && !mFragment.isDetached()) {
            FragmentTransaction fragmentTransaction = mActivity
                    .getFragmentManager().beginTransaction();
            fragmentTransaction.detach(mFragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 탭이 선택 상태가 되면 호출된다.
     *
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), null);
            fragmentTransaction.add(android.R.id.content, mFragment, mTag);
        } else {
            fragmentTransaction.attach(mFragment);
        }
    }

    /**
     * 선택된 탭이 선택해제되면 호출된다.
     *
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (mFragment != null) {
            fragmentTransaction.detach(mFragment);
        }
    }

    /**
     * 선택된 탭이 다시 선택되면 호출된다.
     *
     * @param tab
     * @param fragmentTransaction
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Toast.makeText(mActivity, "onTabReselected!", Toast.LENGTH_SHORT)
                .show();
    }
}
