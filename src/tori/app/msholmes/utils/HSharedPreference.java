package tori.app.msholmes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import tori.app.msholmes.utils.HContants;

public class HSharedPreference {

	/**
	 * SharedPreference에서 key값을 전달하여 value를 얻어낸다.
	 * @param ctx Context
	 * @param key key값
	 * @return key값에 할당된 value값
	 */
	public static String getValue(Context ctx, String key) {
		SharedPreferences sharedPreference = getSharedPreference(ctx);

		String savedValue = sharedPreference.getString(key, "");
		return savedValue;
	}

	/**
	 * SharedPreference에 key값으로 value값을 세팅한다.
	 * @param ctx Context
	 * @param key key값
	 * @param value key값에 할당될 value값
	 */
	public static void setValue(Context ctx, String key, String value) {
		SharedPreferences sharedPreference = getSharedPreference(ctx);
		SharedPreferences.Editor editor = sharedPreference.edit();

		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * SharedPreferece 객체를 얻어낸다.
	 * @param ctx Context
	 * @return 정해진 상수로 구성된 SharedPreference 객체 
	 */
	public static SharedPreferences getSharedPreference(Context ctx) {
		SharedPreferences sharedPreference = ctx.getSharedPreferences(
				HContants.SP_KEY, ctx.MODE_MULTI_PROCESS);
		
		return sharedPreference;
	}
}
