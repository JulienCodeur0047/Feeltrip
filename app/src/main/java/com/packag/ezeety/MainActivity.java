package com.packag.ezeety;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

        CheckConnection();

        buttonGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckConnection()){
                    Intent intentLogin = new Intent(MainActivity.this, ezeety_login.class);
                    startActivity(intentLogin);
                }

            }
        });
        buttonGotoRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckConnection()){
                    Intent intentRegistration = new Intent(MainActivity.this, ezeety_registration.class);
                    startActivity(intentRegistration);
                }

            }
        });
    }

    private boolean CheckConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else {
            AlertDialog.Builder alertNoConnectionInternet = new AlertDialog.Builder(MainActivity.this);
            alertNoConnectionInternet.setTitle("Information");
            alertNoConnectionInternet.setMessage("Internet non disponible, vérifiez votre connectivité Internet et réessayez");
            alertNoConnectionInternet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertNoConnectionInternet.show();
            return false;
        }

    }
}
