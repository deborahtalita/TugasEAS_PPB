package com.example.eas_ppb.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import com.example.eas_ppb.api.JsonPlaceHolderApi;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.api.response.GetAllMenuResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenusRepository {
    private static MenusRepository menusRepository;
    private JsonPlaceHolderApi service;

    public MenusRepository(Application application) {
        service = RestClient.getService();
    }

    public MutableLiveData<GetAllMenuResponse> getAllMenuResponseMutableLiveData() {
        MutableLiveData<GetAllMenuResponse> menus = new MutableLiveData<>();

        Call<GetAllMenuResponse> call = service.getAllMenu();
        call.enqueue(new Callback<GetAllMenuResponse>() {
            @Override
            public void onResponse(Call<GetAllMenuResponse> call, Response<GetAllMenuResponse> response) {
                if (response.isSuccessful()){
                    menus.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetAllMenuResponse> call, Throwable t) {
                menus.setValue(null);
            }
        });
        return menus;
    }
}
