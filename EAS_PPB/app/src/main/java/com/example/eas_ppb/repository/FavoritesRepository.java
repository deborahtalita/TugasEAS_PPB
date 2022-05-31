package com.example.eas_ppb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.eas_ppb.database.FavoritesRoomDatabase;
import com.example.eas_ppb.database.MenuDao;
import com.example.eas_ppb.model.Menu;

import java.util.List;

public class FavoritesRepository {
    private MenuDao mMenuDao;
    private LiveData<List<Menu>> mAllMenus;

    public FavoritesRepository(Application application) {
        FavoritesRoomDatabase db = FavoritesRoomDatabase.getDatabase(application);
        mMenuDao = db.menuDao();
        mAllMenus = mMenuDao.getAscByIDMenus();
    }

    public LiveData<List<Menu>> getAllMenus() {
        return mAllMenus;
    }

    public void insert(Menu menu) {
        FavoritesRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMenuDao.insert(menu);
        });
    }

    public void delete(Menu menu) {
        FavoritesRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMenuDao.delete(menu);
        });
    }
}
