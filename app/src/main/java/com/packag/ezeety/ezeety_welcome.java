package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ezeety_welcome extends AppCompatActivity {

    TextView textViewUserName, textViewTermeandCondition;
    Button buttonNextWelcomePage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezzety_welcome_page);
        textViewUserName = findViewById(R.id.textViewuserName_wp);
        textViewTermeandCondition = findViewById(R.id.texteditTermeandCondition);
        buttonNextWelcomePage = findViewById(R.id.button_next_welcome_page);

        String textTermAndCondition = "<p>En cliquant sur Suivant, vous acceptez nos <b\n" +
                ">Conditions d’utilisation</b>. Découvrez\n" +
                "comment recueillons, utilisons et partageons vos données en lisant notre <b\n" +
                ">Politique d’utilisation des données</b> et\n" +
                "comment nous utilisons les cookies et autres technologies similaires en\n" +
                "consultant notre <b>Politique d’utilisation\n" +
                "des cookies</b>. </p>";

        textViewTermeandCondition.setText(Html.fromHtml(textTermAndCondition));

        Intent i = getIntent();
        textViewUserName.setText(i.getStringExtra("UserName").toString());
        buttonNextWelcomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilgetStart = new Intent(ezeety_welcome.this, ezeety_profil_getstart.class);

                startActivity(intentProfilgetStart);
            }
        });


    }
}
