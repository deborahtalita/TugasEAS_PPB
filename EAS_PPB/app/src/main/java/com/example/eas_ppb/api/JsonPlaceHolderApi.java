package com.example.eas_ppb.api;

import com.example.eas_ppb.api.response.GetAMenuResponse;
import com.example.eas_ppb.api.response.GetAllMenuResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("menu")
    Call<GetAllMenuResponse> getAllMenu();

    @GET("menu/{id}")
    Call<GetAMenuResponse> getMenu(@Path("id") String menuID);
}
