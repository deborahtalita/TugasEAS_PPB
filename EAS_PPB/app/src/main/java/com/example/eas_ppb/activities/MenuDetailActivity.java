package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.eas_ppb.FavoritesRoomDatabase;
import com.example.eas_ppb.FavoritesViewModel;
import com.example.eas_ppb.InternetChecker;
import com.example.eas_ppb.api.response.GetAMenuResponse;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.model.Menu;
import com.example.eas_ppb.R;
import java.util.ArrayList;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Call;

public class MenuDetailActivity extends AppCompatActivity {
    private static final int NEW_FAV_LIST_REQUEST_CODE = 1;
    TextView menuNameDetail, menuDescriptionDetail;
    ImageButton backMenuDetail, favoriteMenuDetail;
    ImageSlider menuImageDetail;
    Menu menuIntent;
    FavoritesViewModel mFavViewModel;
    boolean check = false;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        getSupportActionBar().hide();

        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        //favoriteMenuDetail = findViewById(R.id.imagebutton_FavoriteMenuDetail);
        menuImageDetail = findViewById(R.id.imageslider_MenuImageDetail);
        menuNameDetail = findViewById(R.id.textview_MenuNameDetail);
        menuDescriptionDetail = findViewById(R.id.textview_MenuDescriptionDetail);
        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        mFavViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        menuIntent = getIntent().getParcelableExtra("MENU_DETAILS");

        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_false));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Intent in = new Intent(MenuDetailActivity.this, FavoritesActivity.class);
                    menuIntent.setFavorite(true);
                    mFavViewModel.insert(menuIntent);
                    startActivity(in);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_true));
                }
                else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_false));
                }
            }
        });

//        toggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(check == true){
//                    if(toggleButton.isChecked()){
//                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_true));
//                    }
//                } else if (check){
//                    if(toggleButton.isChecked()){
//                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_false));
//                    }
//                }
//            }
//        });

//        if(menuIntent.isFavorite()){
//            favoriteMenuDetail.setBackgroundResource(R.drawable.ic_favorite_false);
//        } else {
//            favoriteMenuDetail.setBackgroundResource(R.drawable.ic_favorite_true);
//        }


        menuIntent = getIntent().getParcelableExtra("MENU_DETAILS");

        if(InternetChecker.isInternetConnected(this)) {
            getMenuFromAPI();
        } else {
            getMenuFromParcelable();
        }

        backMenuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        favoriteMenuDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                check = true;
////                favoriteMenuDetail.isPressed();
//                mFavViewModel.insert(menuIntent);
//                Toast.makeText(MenuDetailActivity.this,  "masuk", Toast.LENGTH_SHORT).show();
//            }
//        });
//        if(check == true){
//            favoriteMenuDetail.setBackgroundResource(R.drawable.ic_favorite_true);
//        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FAV_LIST_REQUEST_CODE && resultCode == -1) {
            Menu menuFav = data.getParcelableExtra("MENU_DETAILS");
            menuFav.setFavorite(true);
            mFavViewModel.insert(menuFav);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"CODE: "+response.code()+" "+response.errorBody(),Toast.LENGTH_LONG).show();
                    return;
                }
                Menu menu = response.body().getMenu();
                buildUI(menu);
            }

            @Override
            public void onFailure(Call<GetAMenuResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong. Try Again Later!",Toast.LENGTH_LONG).show();
            }
        });
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