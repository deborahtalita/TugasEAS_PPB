package com.example.eas_ppb.api;

import com.example.eas_ppb.api.request.BodyLogin;
import com.example.eas_ppb.api.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("api/v1/auth/login")
    Call<LoginResponse> postLogin(@Body BodyLogin bodyLogin);
}
