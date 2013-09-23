package tori.app.msholmes.activity.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.animation.*;
import android.widget.*;
import tori.app.msholmes.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import tori.app.msholmes.activity.guide.GuideActivity;
import tori.app.msholmes.activity.profile.FollowersActivity;
import tori.app.msholmes.activity.profile.FollowingActivity;
import tori.app.msholmes.activity.profile.SettingsActivity;
import tori.app.msholmes.utils.HCommonUtil;
import tori.app.msholmes.utils.HContants;
import tori.app.msholmes.utils.HSharedPreference;

/**
 * 사용자 프로필 조회 액티비티
 * <p/>사용자 프로필 조회 및 Followers, Following, 설정, 이벤트를 조회할 수 있습니다.
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class MoreFragment extends Fragment {

    /** 비동기 이미지 로딩 라이브러리 */
    private AQuery aQuery;
    /** 내 프로필 사진 */
    private static ImageView imageProfileMe;
    /** 내 프로필 상세보기 */
    private static Button buttonUpdateProfile;
    /** 설정으로 이동 */
    private static Button buttonUpdateSettings;
    /** 닉네임 */
    private static TextView textProfileNick;
    /** 실명 */
    private static TextView textProfileName;
    /** Followers 수 */
    private static TextView textFollowersCnt;
    /** Following 수 */
    private static TextView textFollowingCnt;
    /** 프로필 입력 퍼센트 */
    private static TextView textProfilePercentage;
    /** Followers 레이아웃 */
    private static RelativeLayout relativeFollowers;
    /** Following 레이아웃 */
    private static RelativeLayout relativeFollowing;
    /** 설정으로 이동 레이아웃 */
    private static RelativeLayout relativeProfileSettings;
    /** 공지 레이아웃 */
    private static RelativeLayout relativeProfileNotice;
    /** 공지 닫기 버튼 */
    private static Button buttonCloseNotice;
    /** 공지 제목 */
    private static TextView textNoticeTitle;
    /** 공지 내용 */
    private static TextView textNoticeDesc;
    /** 공지 제목 탬플릿 */
    private String[] arrNoticeTitle;
    /** 공지 내용 탬플릿 */
    private String[] arrNoticeDesc;
    /** 공지 내용 중 공유 관련 버튼 */
    private static Button buttonMoreAction;


    // FIXME : 임시
    private static ImageView imageBanner;
    private String bannerUrl = "http://cafeskthumb.phinf.naver.net/20130724_138/lks040388_1374626383458WnVTj_JPEG/%B6%F3%C4%ED%C5%D9_%B9%E8%B3%CA.jpg?type=w740";


    /**
     * Fragment가 생성되면 호출된다.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 비동기 이미지 호출 객체를 초기화
        aQuery = new AQuery(getActivity().getApplicationContext());
    }

    /**
     * Fragment 뷰를 생성한다.
     *
     * @param inflater           UI 인플레이터
     * @param container          컨테이너
     * @param savedInstanceState 보통은 false
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);
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
        // 내 프로필 이미지
        imageProfileMe = (ImageView) getView().findViewById(R.id.image_profile_me);
        // 내 프로필 수정 이동 버튼
        buttonUpdateProfile = (Button) getView().findViewById(R.id.button_update_profile);
        // 설정으로 이동 버튼
        buttonUpdateSettings = (Button) getView().findViewById(R.id.button_update_settings);
        // 닉네임
        textProfileNick = (TextView) getView().findViewById(R.id.text_profile_nick);
        // 실명
        textProfileName = (TextView) getView().findViewById(R.id.text_profile_name);
        // Followers 수
        textFollowersCnt = (TextView) getView().findViewById(R.id.text_followers_cnt);
        // Following 수
        textFollowingCnt = (TextView) getView().findViewById(R.id.text_following_cnt);
        // 프로필 진행 퍼센트
        textProfilePercentage = (TextView) getView().findViewById(R.id.text_profile_percentage);
        // Followers 레이아웃 
        relativeFollowers = (RelativeLayout) getView().findViewById(R.id.relative_followers);
        // Following 레이아웃 
        relativeFollowing = (RelativeLayout) getView().findViewById(R.id.relative_following);
        // 설정으로 이동 레이아웃
        relativeProfileSettings = (RelativeLayout) getView().findViewById(R.id.relative_profile_settings);
        // 공지사항 레이아웃
        relativeProfileNotice = (RelativeLayout) getView().findViewById(R.id.relative_profile_notice);
        // 공지사항 닫기 버튼
        buttonCloseNotice = (Button) getView().findViewById(R.id.button_close_notice);
        // 공지사항 제목
        textNoticeTitle = (TextView) getView().findViewById(R.id.text_notice_title);
        // 공지사항 내용
        textNoticeDesc = (TextView) getView().findViewById(R.id.text_notice_desc);
        // 공지사항에서 추가 버튼
        buttonMoreAction = (Button) getView().findViewById(R.id.button_more_action);

        // FIXME : 임시
        imageBanner = (ImageView) getView().findViewById(R.id.image_banner);
        aQuery.id(imageBanner).image(bannerUrl, true, true);





        // 클릭 이벤트 처리
        relativeFollowers.setOnClickListener(new ButtonOnClickListener());
        relativeFollowing.setOnClickListener(new ButtonOnClickListener());
        relativeProfileSettings.setOnClickListener(new ButtonOnClickListener());
        buttonCloseNotice.setOnClickListener(new ButtonOnClickListener());
        textProfilePercentage.setOnClickListener(new ButtonOnClickListener());
        buttonMoreAction.setOnClickListener(new ButtonOnClickListener());
    }

    /**
     * 공지사항이 있는 경우 상단에 노출시킨다.
     */
    private void showNoticeIfAvailable() {
        // 탬플릿 문자열을 리소스에서 얻음
        this.getTemplateMsgFromResource();

        // 프로필 정보 입력 숨기기
        relativeProfileNotice.setVisibility(View.GONE);
    }

    /**
     * 탬플릿 메세지를 리소스에서 얻는다.
     */
    private void getTemplateMsgFromResource() {
        arrNoticeTitle =  getResources().getStringArray(R.array.profile_notice_step);
        arrNoticeDesc = getResources().getStringArray(R.array.profile_notice_step_desc);
    }

    /**
     * 프로필 정보 입력 관련 애니메이션을 처리한다.
     * <p/>상단에서 하단으로 이동하면서 alpha 값이 조절되는 애니메이션을 제공한다.
     */
    private void animateNoticeLayout() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(500);
        set.addAnimation(animation);

        LayoutAnimationController controller =
                new LayoutAnimationController(set, 1.0f);
        relativeProfileNotice.setLayoutAnimation(controller);
        relativeProfileNotice.startAnimation(animation);
        relativeProfileNotice.setVisibility(View.VISIBLE);
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
                case R.id.relative_followers:
                    // Followers 레이아웃 클릭 이벤트
                    Intent followersIntent = new Intent(getActivity(), FollowersActivity.class);
                    startActivity(followersIntent);
                    getActivity().overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.relative_following:
                    // Following 레이아웃 클릭 이벤트
                    Intent followingIntent = new Intent(getActivity(), FollowingActivity.class);
                    startActivity(followingIntent);
                    getActivity().overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.button_update_settings:
                case R.id.relative_profile_settings:
                    // 설정으로 이동 레이아웃 클릭 이벤트
                    Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(settingsIntent);
                    getActivity().overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out);
                    break;
                case R.id.text_profile_percentage:
                    // 프로필 진행 퍼센트 클릭 이벤트
                    // TODO : 프로필 진행 퍼센트에 대한 도움말 화면을 보여줌




                    break;
                case R.id.button_close_notice:
                    //  공지사항 레이어 닫기 이벤트
                    relativeProfileNotice.setVisibility(View.GONE);

                    // TODO : 닫기 버튼을 클릭하면, 일정 시간 보이지 않도록 함




                    break;
                case R.id.button_more_action:
                    // 친구와 공유할 클릭 이벤트
                    Log.d("nine", "친구와 함께 핫딜 공유");

                    break;
                default:
                    break;
            }
        }
    }
}
