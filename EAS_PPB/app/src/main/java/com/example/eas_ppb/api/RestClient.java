package com.example.eas_ppb.api;

import com.example.eas_ppb.api.JsonPlaceHolderApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static JsonPlaceHolderApi service;

    public static JsonPlaceHolderApi getService() {
        if(service == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://foodbukka.herokuapp.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            service = retrofit.create(JsonPlaceHolderApi.class);
        }
        return service;
    }

}
