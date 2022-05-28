package com.example.eas_ppb.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static APIInterface service;
    private static Retrofit retrofit;

    public static APIInterface getService() {

        if (service == null) {
            // Membuat base URL
            String BASE_URL = "https://foodbukka.herokuapp.com/";

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.client(httpClient.build()).build();

            service = retrofit.create(APIInterface.class);
        }
        return service;
    }
}
