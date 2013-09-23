package tori.app.msholmes.network;

import retrofit.http.GET;
import retrofit.http.Path;

import com.google.gson.JsonObject;

public interface UserService {

	/**
	 * 이메일이 중복되는지 확인한다. 
	 * @param email
	 * @return
	 */
	@GET("/account/check_email/{email}")		
	JsonObject checkEmail(@Path("email") String email);
	
}
