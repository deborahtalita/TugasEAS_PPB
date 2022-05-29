package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eas_ppb.R;

public class SplashActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        loginBtn = findViewById(R.id.cLoginBtn);
        registerBtn = findViewById(R.id.cRegisterBtn);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = null;
                switch (view.getId()){
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
    }
}