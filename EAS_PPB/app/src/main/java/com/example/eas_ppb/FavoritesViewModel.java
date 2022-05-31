package com.example.eas_ppb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eas_ppb.model.Menu;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository mRepository;

    private final LiveData<List<Menu>> mAllMenus;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavoritesRepository(application);
        mAllMenus = mRepository.getAllMenus();
    }

    public LiveData<List<Menu>> getAllMenus() {
        return mAllMenus;
    }

    public void insert(Menu menu) {
        mRepository.insert(menu);
    }
}
