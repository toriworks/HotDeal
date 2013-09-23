package tori.app.msholmes.activity;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import tori.app.msholmes.utils.HCommonUtil;
import tori.app.msholmes.utils.HContants;
import tori.app.msholmes.utils.HSharedPreference;

import java.util.UUID;

/**
 * 앱 실행시 가장 처음 구동되는 클래스
 * <p/>앱 첫 실행시, 실행되는 클래스로 GCM 관련 작업이나 이미지 관련 초기화 작업을 수행합니다.
 *
 * @author Hyoseok Kim
 * @since 1.0
 */
public class UIApplication extends Application {

    /** Volley 사용을 위한 RequestQueue 선언 */
    private RequestQueue gRequestQueue;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO : GCM 등록 수행
        // 1. 단말기의 UDID를 얻어서 SP(SharedPreference)에 저장
        String deviceUDID = HSharedPreference.getValue(getApplicationContext(), HContants.SP_DEVICE_UDID);
        if(null == deviceUDID || "".equals(deviceUDID)) {
            String tempUDID = getUDIDFromTelephony();
            Log.d("nine", "SP에 저장된 단말기 고유값이 없어서 새로발급:" + tempUDID + "<END");
            HSharedPreference.setValue(getApplicationContext(), HContants.SP_DEVICE_UDID, tempUDID);
        } else {
            Log.d("nine", "SP에 저장된 단말기 고유값:" + HSharedPreference.getValue(getApplicationContext(), HContants.SP_DEVICE_UDID) + "<END");
        }

        // 2. GCM에 단말기 UDID를 등록



        // 프로필 등록 단계 초기화 수행
        String profileStatus = HSharedPreference.getValue(getApplicationContext(), HContants.SP_PROFILE_STATUS);
        if(null == profileStatus || "".equals(profileStatus)) {
            Log.d("nine", "프로필 등록 단계 1단계로 초기화");
            HSharedPreference.setValue(getApplicationContext(), HContants.SP_PROFILE_STATUS, "1");
        }


        // Volley 사용 초기화
        gRequestQueue = Volley.newRequestQueue(this);
    }

    /**
     * 단말기의 고유값을 생성한다.
     * @return 단말기의 고유값
     */
    private String getUDIDFromTelephony() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = HCommonUtil.getStringWithSafe(tm.getDeviceId());
        tmSerial = HCommonUtil.getStringWithSafe(tm.getSimSerialNumber());
        androidId = HCommonUtil.getStringWithSafe(android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        return deviceUuid.toString();
    }

    /**
     * 외부에서 RequestQueue 객체를 얻는다.
     * <p/>Activity에서 필요한 경우에 app.getRequestQueue()로 얻어서 사용한다.
     * @return 큐 객체
     */
    public RequestQueue getgRequestQueue() {
        return gRequestQueue;
    }

}
