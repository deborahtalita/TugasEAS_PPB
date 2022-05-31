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
    FavoritesListAdapter favAdapter;
    private int NEW_FAv_ACTIVITY_REQUEST_CODE = 1;
    Menu menuFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().hide();

        //menuFav = getIntent().getParcelableExtra("MENU_FAV");
//        Intent in = new Intent();
//        Menu menu = in.getParcelableExtra("MENU_DETAIL");
//
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("MENU_DETAIL", menu);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();

        List<String> images = new ArrayList<String>();
        images.add("https://res.cloudinary.com/jobizil/image/upload/v1602768183/images/menus/x4cspjvzqn2qk76sjhiw.jpg");
        Menu menu1 = new Menu(images, "Pounded yam","tes",1);
        //menus.add(menu1);
//        dao.insert(menu1);

        menuList = findViewById(R.id.recyclerview_FavList);
        mFavViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        //buildRecyclerView();

//        mFavViewModel.getAllMenus().observe(this, menus -> {
//            favAdapter.setMenus(menus);
//        });
//        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
//            @Override
//            public void onChanged(List<Menu> menus) {
//                favAdapter.setMenus(menus);
//            }
//        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        FavoritesListAdapter favAdapter = new FavoritesListAdapter(this, menus);
        menuList.setLayoutManager(linearLayoutManager);
        menuList.setHasFixedSize(true);
        menuList.setAdapter(favAdapter);

        //mFavViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(@Nullable List<Menu> menus) {
                favAdapter.setMenus(menus);
            }
        });
        }

    private void buildRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        FavoritesListAdapter favAdapter = new FavoritesListAdapter(this, menus);
        menuList.setLayoutManager(linearLayoutManager);
        menuList.setHasFixedSize(true);
        menuList.setAdapter(favAdapter);

        getAllFavorites();
    }

    private void getAllFavorites(){
        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menus) {
                for(Menu menu: menus){
                    Menu fav = new Menu(menu.getImages(),menu.getMenuname(), menu.getDescription(),menu.getV());
                    menus.add(fav);
                }
                favAdapter.setMenus(menus);
            }
        });
    }
}