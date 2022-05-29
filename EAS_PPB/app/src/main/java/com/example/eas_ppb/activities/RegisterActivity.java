package com.example.eas_ppb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eas_ppb.MainActivity;
import com.example.eas_ppb.R;
import com.example.eas_ppb.api.RestClient;
import com.example.eas_ppb.api.request.BodyRegister;
import com.example.eas_ppb.api.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPhoneNumber, edtPassword, edtConfirmPassword;
    Button registerBtn;
    TextView tvChooseLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        edtUsername = findViewById(R.id.editUsername);
        edtEmail = findViewById(R.id.editEmail);
        edtPhoneNumber = findViewById(R.id.editPhoneNumber);
        edtPassword = findViewById(R.id.editPassword);
        edtConfirmPassword = findViewById(R.id.editConfirmPassword);
        registerBtn = findViewById(R.id.RegisterBtn);
        tvChooseLogin = findViewById(R.id.tvChooseLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordValid()){
                    postRegister();
                }
            }
        });

        tvChooseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });
    }

    private boolean isPasswordValid(){
        String pass1 = edtPassword.getText().toString();
        String pass2 = edtPassword.getText().toString();

        if(pass1.length() >= 6){
            if(pass1.equals(pass2)){
                return true;
            } else {
                Toast.makeText(RegisterActivity.this,  "Password is not matched!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(RegisterActivity.this,  "Password should contain a minimum of 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void postRegister(){
        BodyRegister bodyRegister = new BodyRegister();
        bodyRegister.setUsername(edtUsername.getText().toString());
        bodyRegister.setEmail(edtEmail.getText().toString());
        bodyRegister.setPhoneNumber(edtPhoneNumber.getText().toString());
        bodyRegister.setPassword(edtPassword.getText().toString());

        RestClient.getService().postRegister(bodyRegister).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    // Status code : 200
                    Log.i("Response", response.message());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this,  "Sorry, either username/email has already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}