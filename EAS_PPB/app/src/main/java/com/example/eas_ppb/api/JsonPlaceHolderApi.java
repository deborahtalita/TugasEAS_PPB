package com.example.eas_ppb.api;

import com.example.eas_ppb.api.request.BodyLogin;
import com.example.eas_ppb.api.request.BodyRegister;
import com.example.eas_ppb.api.response.GetAMenuResponse;
import com.example.eas_ppb.api.response.GetAllMenuResponse;
import com.example.eas_ppb.api.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @POST("auth/login")
    Call<LoginResponse> postLogin(@Body BodyLogin bodyLogin);

    @POST("auth/register")
    Call<LoginResponse> postRegister(@Body BodyRegister bodyRegister);

    @GET("menu")
    Call<GetAllMenuResponse> getAllMenu();

    @GET("menu/{id}")
    Call<GetAMenuResponse> getMenu(@Path("id") String menuID);
}
