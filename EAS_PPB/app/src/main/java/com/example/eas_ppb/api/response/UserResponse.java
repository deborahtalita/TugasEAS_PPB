package com.example.eas_ppb.api.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
	@SerializedName("status")
	private String status;

	@SerializedName("token")
	private String token;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponses{" + 
			"status = '" + status + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}