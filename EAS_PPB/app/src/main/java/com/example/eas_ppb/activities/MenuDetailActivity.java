package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.eas_ppb.viewmodel.FavoritesViewModel;
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

    TextView menuNameDetail, menuDescriptionDetail;
    ImageButton backMenuDetail;
    ImageSlider menuImageDetail;
    Menu menuIntent;
    FavoritesViewModel mFavViewModel;
    ToggleButton favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        getSupportActionBar().hide();

        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        menuImageDetail = findViewById(R.id.imageslider_MenuImageDetail);
        menuNameDetail = findViewById(R.id.textview_MenuNameDetail);
        menuDescriptionDetail = findViewById(R.id.textview_MenuDescriptionDetail);
        backMenuDetail = findViewById(R.id.imagebutton_BackMenuDetail);
        mFavViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        menuIntent = getIntent().getParcelableExtra("MENU_DETAILS");

        favButton = (ToggleButton) findViewById(R.id.myToggleButton);
        //favButton.setChecked(false);
        if (menuIntent.isFavorite()){
            Log.i("fav","yes");
            favButton.setChecked(true);
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_true));
        } else {
            favButton.setChecked(false);
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_false));
        }

        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    menuIntent.setFavorite(true);
                    mFavViewModel.insert(menuIntent);
                    Intent in = new Intent(MenuDetailActivity.this, FavoritesActivity.class);
                    startActivity(in);
                    Toast.makeText(MenuDetailActivity.this,  "Added to Favorites!", Toast.LENGTH_SHORT).show();
                    favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_true));
                }
                else {
                    favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_false));
                }
            }
        });

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