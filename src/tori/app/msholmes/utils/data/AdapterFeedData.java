package tori.app.msholmes.utils.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidquery.AQuery;
import tori.app.msholmes.R;
import tori.app.msholmes.utils.domain.Feed;

import java.util.ArrayList;
import java.util.List;

public class AdapterFeedData extends ArrayAdapter<Feed> {

    /** 컨텍스트 객체 */
    private Context context;
    /** 데이터 객체 */
    private List<Feed> listData;
    /** 레이아웃 객체 */
    private int layoutId;
    /** 이미지 로딩 객체 */
    private AQuery aQuery;
    /** 재활용 이미지 로딩 객체 */
    private AQuery reAQuery;
    /** 홀더 객체 */
    private static HolderFeedData holderFeedData;

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param textViewResourceId The resource ID for a layout file containing a TextView to use when
     *                           instantiating views.
     * @param objects            The objects to represent in the ListView.
     */
    public AdapterFeedData(Context context, int textViewResourceId, List<Feed> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.listData = objects;
        this.layoutId = textViewResourceId;

        // 이미지 로딩 객체 초기화
        this.aQuery = new AQuery(this.context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 비동기 이미지 호출 객체
        reAQuery = aQuery.recycle(convertView);
        final int pos = position;

        if(null == convertView) {
            convertView = View.inflate(this.context, this.layoutId, null);

            // 홀더 패턴으로 1회만 로우 세팅
            holderFeedData = new HolderFeedData();
            holderFeedData.textGubun = (TextView) convertView.findViewById(R.id.text_gubun);
            holderFeedData.imageHot = (ImageView) convertView.findViewById(R.id.image_hot);
            holderFeedData.imageClip = (ImageView) convertView.findViewById(R.id.image_clip);
            holderFeedData.imageLike = (ImageView) convertView.findViewById(R.id.image_like);

            holderFeedData.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holderFeedData.textContent = (TextView) convertView.findViewById(R.id.text_content);
            holderFeedData.imageAttach = (ImageView) convertView.findViewById(R.id.image_attach);

            holderFeedData.imageProfile = (ImageView) convertView.findViewById(R.id.image_profile);
            holderFeedData.textProfileNick = (TextView) convertView.findViewById(R.id.text_profile_nick);
            holderFeedData.textRegdate = (TextView) convertView.findViewById(R.id.text_regdate);
            holderFeedData.imageLevel = (ImageView) convertView.findViewById(R.id.image_level);
            holderFeedData.imageShare = (ImageView) convertView.findViewById(R.id.image_share);
            holderFeedData.imageComment = (ImageView) convertView.findViewById(R.id.image_comment);

            convertView.setTag(holderFeedData);
        } else {
            holderFeedData = (HolderFeedData) convertView.getTag();
        }

        // TODO : 데이터를 얻어서 실제 필드에 매핑




        return convertView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        int rowCount;
        rowCount = (null != listData) ? listData.size() : 0;
        return rowCount;
    }
}
