package tori.app.msholmes.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tori.app.msholmes.R;

/**
 * User: kimhyoseok
 * Date: 13. 7. 23
 * Time: 오후 3:08
 */
public class QnAFragment extends Fragment {

    /**
     * Fragment 뷰를 생성한다.
     * @param inflater UI 인플레이터
     * @param container 컨테이너
     * @param savedInstanceState 보통은 false
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_splash, container, false);
        return v;
    }
}
