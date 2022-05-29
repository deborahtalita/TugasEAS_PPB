package com.example.eas_ppb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eas_ppb.model.Menu;
import com.example.eas_ppb.activities.MenuDetailActivity;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.api.response.GetAllMenuResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView menuList;
    private List<Menu> menus;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefreshlayout_main);
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
                    Toast.makeText(getApplicationContext(),"Check Your Connection or Try Again Later",Toast.LENGTH_LONG).show();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        menuList = findViewById(R.id.recyclerview_MenuList);

        Call<GetAllMenuResponse> call = RestClient.getService().getAllMenu();
        call.enqueue(new Callback<GetAllMenuResponse>() {
            @Override
            public void onResponse(Call<GetAllMenuResponse> call, Response<GetAllMenuResponse> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"CODE: "+response.code()+" "+response.errorBody(),Toast.LENGTH_LONG).show();
                    return;
                }
                menus = response.body().getResult();
                buildRecyclerView();
            }

            @Override
            public void onFailure(Call<GetAllMenuResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong. Try Again Later!",Toast.LENGTH_LONG).show();
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