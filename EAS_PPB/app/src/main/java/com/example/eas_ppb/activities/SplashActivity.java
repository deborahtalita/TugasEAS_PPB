package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eas_ppb.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
    }
}