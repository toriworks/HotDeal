package tori.app.msholmes.utils.data;

import java.util.List;

import com.androidquery.AQuery;

import tori.app.msholmes.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterNotificationData<T> extends ArrayAdapter<T>{

	/** 리스트 데이터 객체 */
	private List<T> rowLists;
	/** 컨텍스트 객체 */
	private Context context;
	/** 데이터홀더 객체 */
	private HolderNotificationData viewHolder;
	/** UI로 사용할 뷰 */
	private int resource;
	/** 이미지 비동기 호출용 객체 */
	private static AQuery aq = null;
	/** 재활용용 이미지 비동기 호출용 객체 */
	private static AQuery raq = null;
	
	/**
	 * 생성자 
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public AdapterNotificationData(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		this.rowLists = objects;
		this.context = context;
		this.resource = textViewResourceId;
		
		// 이미지 비동기 객체 생성 
		aq = new AQuery(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 위치값을 불변 값으로 얻기 
		final int pos = position;
		
		// 재활용 가능한 비동기 이미지 호출 객체 생성 
		raq = aq.recycle(convertView);
		if(null == convertView) {
			// 첫 호출시 뷰 객체가 없으므로 신규 생성
			convertView = View.inflate(this.context, this.resource, null);
			
			// findViewId 사용을 줄이기 위한 홀더패턴 적용
			viewHolder = new HolderNotificationData();
			viewHolder.relativeNotificationData = (RelativeLayout) convertView
					.findViewById(R.id.relative_notification_data);
			viewHolder.imageProfile = (ImageView) convertView
					.findViewById(R.id.image_profile);
			viewHolder.textProfileName = (TextView) convertView
					.findViewById(R.id.text_profile_name);
			viewHolder.textNotificationContent = (TextView) convertView
					.findViewById(R.id.text_notification_content);
			viewHolder.textNotificationDate = (TextView) convertView
					.findViewById(R.id.text_notification_date);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (HolderNotificationData) convertView.getTag();
		}
		
		// TODO : 실제 데이터를 얻어서 viewHolder 객체에 삽입 필요 
		
		return convertView;
	}

}
