package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonGotoRegistration, buttonGotoLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_start);
        buttonGotoLogin = findViewById(R.id.buttonGoToLogin);
        buttonGotoRegistration = findViewById(R.id.buttonGoToRegistration);

        buttonGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(MainActivity.this, ezeety_login.class);
                startActivity(intentLogin);
            }
        });
        buttonGotoRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistration = new Intent(MainActivity.this, ezeety_registration.class);
                startActivity(intentRegistration);
            }
        });
    }

}
