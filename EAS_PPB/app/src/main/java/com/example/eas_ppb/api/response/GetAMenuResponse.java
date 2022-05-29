package com.example.eas_ppb.api.response;

import com.example.eas_ppb.model.Menu;
import com.google.gson.annotations.SerializedName;

public class GetAMenuResponse{
	@SerializedName("Result")
	private Menu menu;

	public Menu getMenu(){
		return menu;
	}
}
