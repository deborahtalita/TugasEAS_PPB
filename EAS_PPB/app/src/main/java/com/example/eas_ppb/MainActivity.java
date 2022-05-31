package com.example.eas_ppb;

<<<<<<< HEAD
import androidx.annotation.NonNull;
=======
import static com.example.eas_ppb.activities.LoginActivity.TEXT;

>>>>>>> 0748195b69742e2155a8f5b5b5a5a7a538a4b087
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.eas_ppb.activities.FavoritesActivity;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.eas_ppb.activities.SetNotificationActivity;
=======
import com.example.eas_ppb.activities.SplashActivity;
>>>>>>> 0748195b69742e2155a8f5b5b5a5a7a538a4b087
import com.example.eas_ppb.adapter.Adapter;
import com.example.eas_ppb.api.JsonPlaceHolderApi;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.model.Menu;
import com.example.eas_ppb.activities.MenuDetailActivity;
import com.example.eas_ppb.api.response.GetAllMenuResponse;
import com.example.eas_ppb.viewmodel.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton imagebutton_PopupMenu;
    private androidx.appcompat.widget.SearchView searchMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MenuViewModel viewModel;
    private RecyclerView menuList;
    private List<Menu> menus;
    private Adapter adapter;
    SharedPreferences sharedPreferencesLoginStatus;
    public static final String SHARED_LOGIN_STATUS = "SharedLoginStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sharedPreferencesLoginStatus = getSharedPreferences(SHARED_LOGIN_STATUS, Context.MODE_PRIVATE);

        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout_main);
        searchMenu = findViewById(R.id.searchview_SearchMenu);
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
<<<<<<< HEAD
                        Intent intent;
=======
                        Intent intent = null;
>>>>>>> 0748195b69742e2155a8f5b5b5a5a7a538a4b087
                        switch (menuItem.getItemId()) {
                            case R.id.setNotification:
                                intent = new Intent(MainActivity.this, SetNotificationActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.favoritesMenu:
                                intent = new Intent(MainActivity.this, FavoritesActivity.class);
<<<<<<< HEAD
=======
                                startActivity(intent);
                                return true;
                            case R.id.Logout:
                                SharedPreferences.Editor editor = sharedPreferencesLoginStatus.edit();
                                editor.putString(TEXT,"FALSE");
                                editor.apply();

                                intent = new Intent(MainActivity.this, SplashActivity.class);
>>>>>>> 0748195b69742e2155a8f5b5b5a5a7a538a4b087
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

        menus = new ArrayList<>();
        setupSwipeRefresh();
        setupSearchView();
        buildRecyclerView();

        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        viewModel.getMenusRepository().observe(this, new Observer<GetAllMenuResponse>() {
            @Override
            public void onChanged(GetAllMenuResponse getAllMenuResponse) {
                List<Menu> menuResponse = getAllMenuResponse.getResult();
                menus = menuResponse;
                adapter.setMenus(menus);
            }
        });
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(InternetChecker.isInternetConnected(MainActivity.this)) {
                    Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(refresh);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(getApplicationContext(),"Check Your Connection or Try Again Later",Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupSearchView() {
        searchMenu.clearFocus();
        searchMenu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(InternetChecker.isInternetConnected(MainActivity.this)) {
                    getMenuFromSearch(newText);
                } else {
                    Toast.makeText(getApplicationContext(),"Check Your Connection or Try Again Later",Toast.LENGTH_SHORT).show();
                }
                return false;
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

    private void getMenuFromSearch(String newText) {
        JsonPlaceHolderApi service = RestClient.getService();

        Call<List<Menu>> call = service.searchMenu(newText);
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"ERROR CODE: " + response.code(),Toast.LENGTH_SHORT).show();
                }
                menus = response.body();
                adapter.setMenus(menus);
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong. Try Again Later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}