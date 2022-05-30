package com.example.eas_ppb.activities;

import static com.example.eas_ppb.activities.LoginActivity.SHARED_LOGIN_STATUS;
import static com.example.eas_ppb.activities.LoginActivity.TEXT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.eas_ppb.MainActivity;
import com.example.eas_ppb.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SplashActivity extends AppCompatActivity {
    AppCompatButton loginBtn, registerBtn;
    private SharedPreferences sharedPreferencesLoginStatus;
    private LinearLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        sharedPreferencesLoginStatus = this.getSharedPreferences(SHARED_LOGIN_STATUS, Context.MODE_PRIVATE);

        loginBtn = findViewById(R.id.cLoginBtn);
        registerBtn = findViewById(R.id.cRegisterBtn);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = null;
                switch (view.getId()) {
                    case R.id.cLoginBtn:
                        in = new Intent(SplashActivity.this, LoginActivity.class);
                        break;
                    case R.id.cRegisterBtn:
                        in = new Intent(SplashActivity.this, RegisterActivity.class);
                        break;
                }
                startActivity(in);
            }
        };
        loginBtn.setOnClickListener(onButtonClickListener);
        registerBtn.setOnClickListener(onButtonClickListener);

        if (sharedPreferencesLoginStatus.getString(TEXT, "There!").equals("TRUE")) {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }, 3000L); //3000 L = 3 second
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
                sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);

                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }, 3000L); //3000 L = 3 second
        }
    }


    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}