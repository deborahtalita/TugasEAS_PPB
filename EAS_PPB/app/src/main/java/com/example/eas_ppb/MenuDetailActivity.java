package com.example.eas_ppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDetailActivity extends AppCompatActivity {
    TextView menuNameDetail, menuDescriptionDetail;
    ImageSlider menuImageDetail;
    ImageButton backMenuDetail, favoriteMenuDetail;
    Menu menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        getSupportActionBar().hide();

        menuImageDetail = findViewById(R.id.imageslider_MenuImageDetail);
        menuNameDetail = findViewById(R.id.textview_MenuNameDetail);
        menuDescriptionDetail = findViewById(R.id.textview_MenuDescriptionDetail);
        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        favoriteMenuDetail = findViewById(R.id.imagebutton_FavoriteMenuDetail);

        menuIntent = getIntent().getParcelableExtra("MENU_DETAILS");

        //CEK KONEKSI INTERNET
        if(isInternetConnected()) {
            getMenuFromAPI();
        } else {
            getMenuFromParcelable();
        }
    }

    private void getMenuFromParcelable() {
        buildUI(menuIntent);

    }

    private void getMenuFromAPI() {
        String menuID = menuIntent.getId();

        Call<GetAMenuResponse> call = RestClient.getService().getMenu(menuID);
        call.enqueue(new Callback<GetAMenuResponse>() {
            @Override
            public void onResponse(Call<GetAMenuResponse> call, Response<GetAMenuResponse> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                Menu menu = response.body().getMenu();
                buildUI(menu);
            }

            @Override
            public void onFailure(Call<GetAMenuResponse> call, Throwable t) {

            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null) {
            if(networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void buildUI(Menu menu) {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        for(int i = 0; i < menu.getImages().size(); i++){
            slideModels.add(new SlideModel(menu.getImages().get(i), ScaleTypes.CENTER_CROP));
        }

        menuImageDetail.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        menuNameDetail.setText(menu.getMenuname());
        menuDescriptionDetail.setText(menu.getDescription());
    }
}