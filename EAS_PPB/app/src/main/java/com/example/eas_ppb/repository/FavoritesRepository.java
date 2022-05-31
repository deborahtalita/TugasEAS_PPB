package com.example.eas_ppb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.eas_ppb.FavoritesRoomDatabase;
import com.example.eas_ppb.MenuDao;
import com.example.eas_ppb.model.Menu;

import java.util.List;

public class FavoritesRepository {
    private MenuDao mMenuDao;
    private LiveData<List<Menu>> mAllMenus;

    public FavoritesRepository(Application application) {
        FavoritesRoomDatabase db = FavoritesRoomDatabase.getDatabase(application);
        mMenuDao = db.menuDao();
        mAllMenus = mMenuDao.getAlphabetizedMenus();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Menu>> getAllMenus() {
        return mAllMenus;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
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
