package com.packag.ezeety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ezeety_home extends AppCompatActivity {

    TextView textViewRecommandation, textViewAmbassadeur, textViewParcours;
    EditText editTextSearch;
    ImageView imageViewButtonToGridStyle, imageViewButtonToListStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ezeety_home);
        textViewRecommandation = (TextView) findViewById(R.id.textViewButtonRecom);
        textViewAmbassadeur = (TextView) findViewById(R.id.textViewButtonAmbassad);
        textViewParcours = (TextView) findViewById(R.id.textViewButtonParcr);
        editTextSearch = (EditText) findViewById(R.id.editTextSearchHome);
        imageViewButtonToGridStyle = (ImageView) findViewById(R.id.listGridStyle);
        imageViewButtonToListStyle = (ImageView) findViewById(R.id.listlisStyle);
    }
}
