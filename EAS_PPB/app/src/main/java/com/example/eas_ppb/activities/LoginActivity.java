package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eas_ppb.R;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.api.request.BodyLogin;
import com.example.eas_ppb.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferencesLoginStatus;
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvRegister;

    public static final String SHARED_LOGIN_STATUS = "SharedLoginStatus";
    public static final String TEXT = "FALSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sharedPreferencesLoginStatus = this.getSharedPreferences(SHARED_LOGIN_STATUS, Context.MODE_PRIVATE);

        edtUsername = findViewById(R.id.editUsername);
        edtPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.LoginBtn);
        tvRegister = findViewById(R.id.tvChooseRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postLogin();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(in);
            }
        });
    }

    private void postLogin(){
        BodyLogin bodyLogin = new BodyLogin();
        bodyLogin.setUsername(edtUsername.getText().toString());
        bodyLogin.setPassword(edtPassword.getText().toString());

        RestClient.getService().postLogin(bodyLogin).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    // Status code : 200
                    Log.i("Response", response.message());
                    SharedPreferences.Editor editor = sharedPreferencesLoginStatus.edit();
                    editor.putString(TEXT,"TRUE");
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Please provide a valid username and password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }
}