package tori.app.msholmes;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * GCM(Google Cluod Messaging) 서비스 처리 클래스
 * <p/>
 * GCM을 이용하기 위해서는 앱에서 이 클래스를 서비스(as Service)로 구동하여야 합니다.
 * GCM은 백그라운드 서비스로 동작하며, 서버에서 GCM으로 요청을 하였을 때 GCM 서버에서 단말기로
 * 메세지를 전송합니다. 이 전송된 메세지를 단말기에서 받아서 처리하는 클래스입니다.
 * <p/>
 * GCM 서버에서 전송된 메세지는 onMessage(Context arg0, Intent arg1)로 받으며, 이 메소드 내에서
 * 메세지에 대해서 어떻게 처리할 지 여부를 결정합니다.
 *
 * @author      Hyoseok Kim
 * @since       1.0
 */
public class GCMIntentService extends GCMBaseIntentService {

    /** 전송자 아이디(서버에 등록된 Project ID) */
//	private static final String SENDER_ID = HavitConstants.GCM_ID;
    /** 수신 메세지 */
	private String receivedMessage;
    /** 전달받은 뱃지 카운트 */
	private String badgeCount;

    /**
     * 기본 생성자
     */
	public GCMIntentService() {
//		super(SENDER_ID);
	}

    /**
     * 메세지를 전송받는다.
     * @param context 컨텍스트 객체
     * @param intent 호출 인텐트 객체
     */
	public void onReceive(Context context, Intent intent) {
		Log.d("shp", "onReceive called...");
	}

    /**
     * 단말기 정보가 GCM 서버에 등록되면 호출된다.
     * @param arg0 컨텍스트
     * @param registrationId 서버에 등록된 단말기 정보 아이디
     */
	@Override
	protected void onRegistered(Context arg0, String registrationId) {
//		Log.d("shp", "Device registered: regId = " + registrationId);
//		Log.d("shp", "GCM 발급 아이디:" + registrationId);
//		//
//		// GCM 값을 SP에 저장
//		HavitSharedPref.setValue(getApplicationContext(), HavitConstants.SHARED_SAVED_GCM, registrationId);
	}

    /**
     * 단말기 정보가 GCM 서버에 등록해제되면 호출된다.
     * @param arg0 컨텍스트
     * @param arg1 해제된 아이디
     */
	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.d("shp", "unregistered = " + arg1);  
	}

    /**
     * GCM 서버에서 메세지를 전송하면 수신받는다.
     * <p/>
     * 실제로 알림 메세지 수신 후에, 세부 작업을 해당 메소드에서 수행한다. 해빛통의 경우에는
     * 푸시 알림 메시지의 뱃지 카운트를 MainActivitySliding 클래스의 상단에 표시하기 위해서
     * SharedPreference에 뱃지 카운트를 임시 저장한다.
     * <p/>
     * 또한, 메세지가 온 경우에 단말기의 진동 서비스(Vibration Service)를 활용하여 사용자가 인지할
     * 수 있도록 한다.
     * <p/>
     * 알림은 서버에서 사용자가 정한 규칙에 의해 필터링(Filtering)하며, 단말기에서는 따로 필터링을
     * 수행하지 않는다.
     *
     * @param arg0 컨텍스트
     * @param arg1 호출 인텐트
     */
	@Override
	protected void onMessage(Context arg0, Intent arg1) {
//		if (arg1.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
//			receivedMessage = arg1.getExtras().getString("message");
//			receivedMessage = URLDecoder.decode(receivedMessage);
//
//			// 배찌 카운트를 서버에서 얻은 후에, SP에 넣음
//			if(null != arg1.getExtras().getString("badgeCount")) {
//				badgeCount = arg1.getExtras().getString("badgeCount");
//				HavitSharedPref.setValue(getApplicationContext(), HavitConstants.SHARED_SAVED_BADGE_COUNT, badgeCount);
//			}
//
//			Log.d("shp", "GCM RECEIVED:" + receivedMessage + ", Count:" + badgeCount);
//			//
//			// 노티피케이션 출력(프로필 설정에서 휴대폰 알림을 받는 경우에만 푸시 표시)
//            if(HavitSharedPref.getValue(getApplicationContext(), HavitConstants.SHARED_SAVED_ACCEPT_PUSH).equals("Y")
//            		|| HavitSharedPref.getValue(getApplicationContext(), HavitConstants.SHARED_SAVED_ACCEPT_PUSH).equals("")) {
//                NotificationUtil.showNotification(getApplicationContext(), "해빛통 알림", "알림도착",
//                        receivedMessage, PushPortalActivity.class);
//                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                long[] pattern = {100, 100, 100, 200, 100, 300};
//                vibe.vibrate(pattern, -1);
//            }
//		}
	}

    /**
     * 메세지 수신 중 에러가 발생하면 호출된다.
     * @param arg0 컨텍스트
     * @param errorId 에러 아이디
     */
	@Override
	protected void onError(Context arg0, String errorId) {
		Log.d("shp", "Received error: " + errorId);
	}

    /**
     * 메세지 수신 중 복구가능한 메세지를 처리한지 여부를 알아낸다.
     * @param context 컨텍스트
     * @param errorId 에러 아이디
     * @return 복구 가능여부 참, 거짓 값
     */
	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return super.onRecoverableError(context, errorId);
	}
}
