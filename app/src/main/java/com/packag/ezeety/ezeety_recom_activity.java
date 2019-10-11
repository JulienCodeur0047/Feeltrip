package com.packag.ezeety;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


public class ezeety_recom_activity extends AppCompatActivity

{

    ListView listViewRecommandation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_recommandation);

        listViewRecommandation = (ListView) findViewById(R.id.listViewListeRecomandation);


    }
}
