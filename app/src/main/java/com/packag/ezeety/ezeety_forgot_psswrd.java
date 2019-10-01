package com.packag.ezeety;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class ezeety_forgot_psswrd extends AppCompatActivity {

    EditText editTextVerifyEmail;
    Button buttonGetPasswordChange;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_forgot_password);
        editTextVerifyEmail = findViewById(R.id.editTextEmailVerify);
        buttonGetPasswordChange = findViewById(R.id.buttonRecuperePasswrd);
    }
}
