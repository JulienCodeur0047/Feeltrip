package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ezeety_registration_second extends AppCompatActivity {

    Button buttonNext2;
    EditText editTextNameuserReg2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_second_step);
        buttonNext2 = findViewById(R.id.buttonNextRegistrationSecond);
        editTextNameuserReg2 = findViewById(R.id.edittextNameSecondReg);

        buttonNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWelcom = new Intent(ezeety_registration_second.this, ezeety_welcome.class);
                startActivity(intentWelcom);
            }
        });
    }

}
