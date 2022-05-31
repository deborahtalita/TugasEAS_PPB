package com.example.eas_ppb.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eas_ppb.Adapter;
import com.example.eas_ppb.FavoritesListAdapter;
import com.example.eas_ppb.FavoritesViewModel;
import com.example.eas_ppb.MainActivity;
import com.example.eas_ppb.R;
import com.example.eas_ppb.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView menuList;
    private FavoritesViewModel mFavViewModel;
    private List<Menu> menus = new ArrayList<>();
    Adapter favAdapter;
    private int NEW_FAv_ACTIVITY_REQUEST_CODE = 1;
    Menu menuFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().hide();

        menuList = findViewById(R.id.recyclerview_FavList);

        menus = new ArrayList<>();
        buildRecyclerView();

        mFavViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(@Nullable List<Menu> menus) {
                favAdapter.setMenus(menus);
            }
        });
        }

    private void buildRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favAdapter = new Adapter(this, menus);
        menuList.setLayoutManager(linearLayoutManager);
        menuList.setHasFixedSize(true);
        menuList.setAdapter(favAdapter);
    }

    private void getAllFavorites(){
        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menus) {
                for(Menu menu: menus){
                    Menu fav = new Menu(menu.getImages(),menu.getMenuname(), menu.getDescription(),menu.getV(), menu.isFavorite());
                    menus.add(fav);
                }
                favAdapter.setMenus(menus);
            }
        });
    }
}