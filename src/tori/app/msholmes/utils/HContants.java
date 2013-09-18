package tori.app.msholmes.utils;

public interface HContants {

	/** Notification용 GCM 아이디 */
	String GCM_ID = "";
	/** SharedPreference용 상수 */
	String SP_KEY = "ms.holmes";
	/** 자동로그인 활성화 여부 */
	String SP_AUTO_LOGIN = "sp.auto.login";


    // ************************************************************************
    // SharedPreference 관련 상수
    // ************************************************************************
    /** 프로필 변경 현황 값 */
    String SP_PROFILE_STATUS = "sp.profile.status";


    // ************************************************************************
	// 공통
    // ************************************************************************
	/** YES */
	String YES = "Y";
	/** NO */
	String NO = "N";
	/** SUCCESS */
	String SUCCESS = "T";
	/** FAIL */
	String FAIL = "F";


    // ************************************************************************
    // 서비스 연동
    // ************************************************************************
	/** API 엔드포인트 */
	String API_ENDPOINT = "http://192.168.123.121:8080/";
    /** GCM용 디바이스 고유 아이디 */
    String SP_DEVICE_UDID = "sp.device.udid";
}
