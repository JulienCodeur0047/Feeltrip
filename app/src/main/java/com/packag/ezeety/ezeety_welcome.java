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

    TextView textViewUserName, textViewTermeandCondition, textViewbuttonChangeUsername;
    Button buttonNextWelcomePage;
    ezeety_registration_second ezeety_registration_second = new ezeety_registration_second();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezzety_welcome_page);
        textViewUserName = findViewById(R.id.textViewuserName_wp);
        textViewTermeandCondition = findViewById(R.id.texteditTermeandCondition);
        buttonNextWelcomePage = findViewById(R.id.button_next_welcome_page);
        textViewbuttonChangeUsername = findViewById(R.id.texteditButtonChangeUserName);
        Intent i = getIntent();
        final String nomPrenom =  i.getStringExtra("UserNameFirstNameReg");
        final String email = i.getStringExtra("emailReg");
        final String password = i.getStringExtra("passwordReg");
        final String Regusername = i.getStringExtra("UserNameReg");
        final String RegVille = i.getStringExtra("VilleReg");
        final String RegPays = i.getStringExtra("pays");
        final String RegDateNaissace = i.getStringExtra("DateNaissance");
        final String ReggooglePlaceId = i.getStringExtra("place_id");
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
                Intent intentProfilgetStart = new Intent(ezeety_welcome.this, ezeety_profil_getstart.class);

                /*intentProfilgetStart.putExtra("UserNameFirstNameReg",nomPrenom);
                intentProfilgetStart.putExtra("emailReg",email);
                intentProfilgetStart.putExtra("passwordReg",password);
                intentProfilgetStart.putExtra("UserNameReg",Regusername);
                intentProfilgetStart.putExtra("VilleReg",RegVille);
                intentProfilgetStart.putExtra("pays",RegPays);
                intentProfilgetStart.putExtra("DateNaissance",RegDateNaissace);
                intentProfilgetStart.putExtra("place_id",ReggooglePlaceId);*/

                startActivity(intentProfilgetStart);
            }
        });


    }
}
