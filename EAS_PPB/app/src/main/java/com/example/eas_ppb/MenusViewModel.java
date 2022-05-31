package com.example.eas_ppb;


import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eas_ppb.api.response.GetAllMenuResponse;


public class MenuViewModel extends AndroidViewModel {

    private LiveData<GetAllMenuResponse> liveData;

    private MenusRepository menusRepository;

    public MenuViewModel(@NonNull Application application) {
        super(application);
        menusRepository = new MenusRepository(application);
        liveData = menusRepository.getAllMenuResponseMutableLiveData();
    }

    public LiveData<GetAllMenuResponse> getMenusRepository() {
        return liveData;
    }

}