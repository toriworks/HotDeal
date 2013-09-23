package tori.app.msholmes.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.androidquery.AQuery;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import tori.app.msholmes.R;
import tori.app.msholmes.activity.profile.FollowersActivity;
import tori.app.msholmes.activity.profile.FollowingActivity;
import tori.app.msholmes.activity.profile.SettingsActivity;
import tori.app.msholmes.utils.HContants;
import tori.app.msholmes.utils.data.AdapterFeedData;
import tori.app.msholmes.utils.domain.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * 국내핫딜 목록 조회 액티비티
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class DomesticFragment extends Fragment {

    /** 비동기 이미지 로딩 라이브러리 */
    private AQuery aQuery;
    /** 공지 레이아웃 */
    private static RelativeLayout relativeFeedNotice;
    /** 공지 닫기 버튼 */
    private static Button buttonCloseNotice;
    /** 공지 제목 */
    private static TextView textNoticeTitle;
    /** 공지 내용 */
    private static TextView textNoticeDesc;
    /** 공지 내용 중 공유 관련 버튼 */
    private static Button buttonMoreAction;
    /** 리스트 뷰 */
    private static PullToRefreshListView pullToRefreshListView;
    /** 로딩 방향(아래 또는 위) */
    private static String loadingDirection;
    /** 피드 어뎁터 */
    private AdapterFeedData adapterFeedData;
    /** 피드 데이터 선언 및 초기화 */
    private List<Feed> listFeedData = new ArrayList<Feed>();

    /**
     * Fragment 뷰를 생성한다.
     * @param inflater UI 인플레이터
     * @param container 컨테이너
     * @param savedInstanceState 보통은 false
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_domestic, container, false);
        return v;
    }

    /**
     * 액티비티가 생성된 후에 호출된다.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 뷰 컴포넌트 초기화 설정
        this.initViewComponents();

        // 공지사항이 있는 경우 보이도록 함
        this.showNoticeIfAvailable();
    }

    /**
     * 화면의 뷰 컴포넌트들을 초기화한다.
     */
    private void initViewComponents() {
        // 공지사항 레이아웃
        relativeFeedNotice = (RelativeLayout) getView().findViewById(R.id.relative_notice);
        // 공지사항 닫기 버튼
        buttonCloseNotice = (Button) getView().findViewById(R.id.button_close_notice);
        // 공지사항 제목
        textNoticeTitle = (TextView) getView().findViewById(R.id.text_notice_title);
        // 공지사항 내용
        textNoticeDesc = (TextView) getView().findViewById(R.id.text_notice_desc);
        // 공지사항에서 추가 버튼
        buttonMoreAction = (Button) getView().findViewById(R.id.button_more_action);

        // 버튼 이벤트 처리
        buttonCloseNotice.setOnClickListener(new ButtonOnClickListener());



        // 리스트뷰 관련 초기화 수행
        this.initListView();
    }

    /**
     * 리스트뷰 관련 초기화를 수행한다.
     */
    private void initListView() {
        // 리스트 뷰
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.pull_refresh_list);

        // 제공 모드 설정(위, 아래 모두 Pull-to-Refresh 적용)
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        // 리프레쉬 리스너
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            /**
             * onRefresh will be called for both a Pull from start, and Pull from
             * end
             */
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // 업데이트 시간 라벨 설정
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // 당기는 방향에 따라서 새로고침 또는 더보기 수행
                if(loadingDirection.equals("PULL_FROM_START")) {
                    loadData(HContants.LOAD_REFRESH);
                } else {
                    loadData(HContants.LOAD_MORE);
                }
            }
        });

        // 당기는 방향 감지 리스너
        pullToRefreshListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {

            /**
             * Called when the internal state has been changed, usually by the user
             * pulling.
             *
             * @param refreshView - View which has had it's state change.
             * @param state       - The new state of View.
             * @param direction   - One of {@link com.handmark.pulltorefresh.library.PullToRefreshBase.Mode#PULL_FROM_START} or
             *                    {@link com.handmark.pulltorefresh.library.PullToRefreshBase.Mode#PULL_FROM_END} depending on which direction
             *                    the user is pulling. Only useful when <var>state</var> is
             *                    {@link com.handmark.pulltorefresh.library.PullToRefreshBase.State#PULL_TO_REFRESH} or
             *                    {@link com.handmark.pulltorefresh.library.PullToRefreshBase.State#RELEASE_TO_REFRESH}.
             */
            @Override
            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                // 방향정보만 획득수 변수에 저장
                loadingDirection = direction.name();
            }
        });

        // 어뎁터 설정
        // FIXME : 임시 데이터 설정
        for(int i=0; i<0; i++) {
            Feed tempFeed = new Feed();
            listFeedData.add(tempFeed);
        }

        adapterFeedData = new AdapterFeedData(getActivity(), R.layout.list_feed_data, listFeedData);
        pullToRefreshListView.setAdapter(adapterFeedData);
    }

    /**
     * 공지사항이 있는 경우 상단에 노출시킨다.
     */
    private void showNoticeIfAvailable() {
        Log.d("nine", "공지사항이 있는 경우 상단에 노출");
    }

    /**
     * 새로 데이터를 얻어온다.
     * @param loadingType 데이터를 얻는 방향
     */
    private void loadData(int loadingType) {
        Log.d("nine", "당기는방향:" + loadingType + "<<END");
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
                case R.id.button_close_notice:
                    //  공지사항 레이어 닫기 이벤트
                    relativeFeedNotice.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}
