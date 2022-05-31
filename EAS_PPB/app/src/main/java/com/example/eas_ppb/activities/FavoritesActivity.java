package com.example.eas_ppb.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.eas_ppb.adapter.Adapter;
import com.example.eas_ppb.viewmodel.FavoritesViewModel;
import com.example.eas_ppb.R;
import com.example.eas_ppb.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView menuList;
    ImageButton backMenuDetail;
    private FavoritesViewModel mFavViewModel;
    private List<Menu> menus = new ArrayList<>();
    Adapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().hide();

        menuList = findViewById(R.id.recyclerview_FavList);
        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        mFavViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        buildRecyclerView();

        mFavViewModel.getAllMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(@Nullable List<Menu> menus) {
                favAdapter.setMenus(menus);
            }
        });

        backMenuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buildRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favAdapter = new Adapter(this, menus);
        menuList.setLayoutManager(linearLayoutManager);
        menuList.setHasFixedSize(true);
        menuList.setAdapter(favAdapter);

        favAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Menu menu) {
                Intent intent = new Intent(FavoritesActivity.this, MenuDetailActivity.class);
                intent.putExtra("MENU_DETAILS", menu);
                startActivity(intent);
            }
        });
    }
}