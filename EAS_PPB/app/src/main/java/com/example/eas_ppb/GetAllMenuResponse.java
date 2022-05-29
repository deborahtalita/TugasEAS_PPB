package com.example.eas_ppb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllMenuResponse {
	@SerializedName("Total Menu")
	private int totalMenu;
	@SerializedName("Result")
	private List<Menu> result;

	public int getTotalMenu(){
		return totalMenu;
	}

	public List<Menu> getResult(){
		return result;
	}
}