package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ezeety_registration extends AppCompatActivity {

    TextView textViewLogin;
    Button buttonNextStep1, buttonRegByfacebook, buttonRegByGoogle;
    CheckBox checkBoxSavePswd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_first_step);

        textViewLogin = findViewById(R.id.textViewLogin);
        buttonNextStep1 = findViewById(R.id.buttonNextRegistrationFirst);
        buttonRegByfacebook = findViewById(R.id.buttonRegistrationFacebook);
        buttonRegByGoogle = findViewById(R.id.buttonRegistrationGoogle);
        checkBoxSavePswd = findViewById(R.id.checkboxSavePasswordRegistration);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(ezeety_registration.this, ezeety_login.class);
                startActivity(intentLogin);
            }
        });
        buttonNextStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSecond = new Intent(ezeety_registration.this, ezeety_registration_second.class);

                startActivity(intentSecond);
            }
        });
    }
}
