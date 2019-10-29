package com.packag.ezeety.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.packag.ezeety.R;
import com.packag.ezeety.ezeety_profil_getstart;


public class WelcomeActivity extends AppCompatActivity {

    TextView textViewUserName, textViewTermeandCondition, textViewbuttonChangeUsername;
    Button buttonNextWelcomePage;
    RegistrationNextActivity ezeety_registration_second = new RegistrationNextActivity();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezzety_welcome_page);
        textViewUserName = findViewById(R.id.textViewuserName_wp);
        textViewTermeandCondition = findViewById(R.id.texteditTermeandCondition);
        buttonNextWelcomePage = findViewById(R.id.button_next_welcome_page);
        textViewbuttonChangeUsername = findViewById(R.id.texteditButtonChangeUserName);

        String textTermAndCondition = "<p>En cliquant sur Suivant, vous acceptez nos <b\n" +
                ">Conditions d’utilisation</b>. Découvrez\n" +
                "comment recueillons, utilisons et partageons vos données en lisant notre <b\n" +
                ">Politique d’utilisation des données</b> et\n" +
                "comment nous utilisons les cookies et autres technologies similaires en\n" +
                "consultant notre <b>Politique d’utilisation\n" +
                "des cookies</b>. </p>";

        textViewbuttonChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textViewTermeandCondition.setText(Html.fromHtml(textTermAndCondition));


        textViewUserName.setText(ezeety_registration_second.RegUsername);
        buttonNextWelcomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilgetStart = new Intent(WelcomeActivity.this, ezeety_profil_getstart.class);
                startActivity(intentProfilgetStart);
            }
        });


    }
}
