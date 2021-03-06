package com.example.eas_ppb.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.eas_ppb.model.Menu;

import java.util.List;

@Dao
public interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Menu menu);

    @Delete
    void delete(Menu menu);

    @Query("DELETE FROM menus_table")
    void deleteAll();

    @Query("SELECT * FROM menus_table ORDER BY menu_id ASC")
    LiveData<List<Menu>> getAscByIDMenus();
}
