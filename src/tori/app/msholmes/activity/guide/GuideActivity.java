package tori.app.msholmes.activity.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.viewpagerindicator.CirclePageIndicator;
import tori.app.msholmes.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class GuideActivity extends Activity {

    /** 뷰 페이저 객체 */
    private static ViewPager pager;
    /** 뷰 페이저에서 보여줄 화면의 총 수 */
    private static final int PAGER_SCREEN_CNT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

        // 화면 컴포넌트 초기화
        this.initScreenComponents();
	}

    /**
     * 화면 컴포넌트를 초기화한다.
     */
    private void initScreenComponents() {
        // 뷰 페이저를 얻음, 뷰 페이저는 항상 어뎁터 클래스를 포함해야 함
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new GuidePagerAdapter(getApplicationContext()));


        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setFillColor(R.color.dft_red_font_color);
        circlePageIndicator.setViewPager(pager);
    }

    /**
     * 페이저 클릭 이벤트 리스너를 생성한다.
     */
    private View.OnClickListener pagerListener = new View.OnClickListener() {

        /**
         * Called when a view has been clicked.
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            // TODO : 클릭된 인덱스 처리
            Log.d("nine", "클릭된 뷰 인덱스:" + vId + "<<END");
        }
    };

    /**
     * 뷰 페이저와 연결되는 어뎁터 클래스
     *
     * @author Hyoseok Kim
     * @since 1.0
     */
    private class GuidePagerAdapter extends PagerAdapter {

        /** 페이저 생성용 인플레이터 */
        private LayoutInflater inflater;

        public GuidePagerAdapter(Context applicationContext) {
            super();
            // 인플레이터 생성
            inflater = LayoutInflater.from(applicationContext);
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position==0){
                v = inflater.inflate(R.layout.g_guide_1, null);
//                v.findViewById(R.id.iv_one);
//                v.findViewById(R.id.btn_click).setOnClickListener(pagerListener);
            }
            else if(position==1){
                v = inflater.inflate(R.layout.g_guide_2, null);
//                v.findViewById(R.id.iv_two);
//                v.findViewById(R.id.btn_click_2).setOnClickListener(pagerListener);
            }else{
                v = inflater.inflate(R.layout.g_guide_3, null);
//                v.findViewById(R.id.iv_three);
//                v.findViewById(R.id.btn_click_3).setOnClickListener(pagerListener);
            }

            ((ViewPager)pager).addView(v, 0);

            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        /**
         * 뷰 페이저 내에서 보여줄 뷰의 총 갯수를 얻는다.
         * @return 뷰의 총 갯수
         */
        @Override
        public int getCount() {
            return GuideActivity.PAGER_SCREEN_CNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}
