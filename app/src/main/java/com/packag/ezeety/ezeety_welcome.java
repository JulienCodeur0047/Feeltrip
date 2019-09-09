package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ezeety_welcome extends AppCompatActivity {

    TextView textViewUserName;
    Button buttonNextWelcomePage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezzety_welcome_page);
        textViewUserName = findViewById(R.id.textViewuserName_wp);
        buttonNextWelcomePage = findViewById(R.id.button_next_welcome_page);

        buttonNextWelcomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilgetStart = new Intent(ezeety_welcome.this, ezeety_profil_getstart.class);
                startActivity(intentProfilgetStart);
            }
        });


    }
}
