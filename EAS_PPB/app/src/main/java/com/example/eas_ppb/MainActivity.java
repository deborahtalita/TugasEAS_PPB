package com.example.eas_ppb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.eas_ppb.activities.FavoritesActivity;
import com.example.eas_ppb.model.Menu;
import com.example.eas_ppb.activities.MenuDetailActivity;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.api.response.GetAllMenuResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ImageButton imagebutton_PopupMenu;
    private RecyclerView menuList;
    private List<Menu> menus;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        menuList = findViewById(R.id.recyclerview_MenuList);
        imagebutton_PopupMenu = findViewById(R.id.imagebuttonPopupMenu);
        imagebutton_PopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.more_option, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.favoritesMenu:
                                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                                startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        Call<GetAllMenuResponse> call = RestClient.getService().getAllMenu();
        call.enqueue(new Callback<GetAllMenuResponse>() {
            @Override
            public void onResponse(Call<GetAllMenuResponse> call, Response<GetAllMenuResponse> response) {
                if(!response.isSuccessful()) {
                    //toast
                    return;
                }
                menus = response.body().getResult();
                buildRecyclerView();
            }

            @Override
            public void onFailure(Call<GetAllMenuResponse> call, Throwable t) {
                //toast
            }
        });
    }

    private void buildRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new Adapter(this, menus);
        menuList.setLayoutManager(linearLayoutManager);
        menuList.setHasFixedSize(true);
        menuList.setAdapter(adapter);

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Menu menu) {
                Intent intent = new Intent(MainActivity.this, MenuDetailActivity.class);
                intent.putExtra("MENU_DETAILS", menu);
                startActivity(intent);
            }
        });
    }
}