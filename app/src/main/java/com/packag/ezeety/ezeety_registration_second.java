package com.packag.ezeety;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ezeety_registration_second extends AppCompatActivity {

    Button buttonNext2;
    EditText editTextNameuserReg, editTextUserNameReg, editTextdateNaissanceReg, editTextVilleReg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_second_step);
        buttonNext2 = findViewById(R.id.buttonNextRegistrationSecond);
        editTextNameuserReg = findViewById(R.id.edittextNameFirstNameReg);
        editTextUserNameReg = findViewById(R.id.editTextUserNamesecondeReg);
        editTextdateNaissanceReg = findViewById(R.id.edittextDatenaissanceReg);
        editTextVilleReg = findViewById(R.id.editTextVilleSecondReg);


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);

                editTextdateNaissanceReg.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextdateNaissanceReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ezeety_registration_second.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonNext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String NameFirstNameUser = editTextNameuserReg.getText().toString(),
                        UserName = editTextUserNameReg.getText().toString(),
                        Ville = editTextVilleReg.getText().toString();
                Date DateNaissance = null;
                try {
                    DateNaissance = dateFormat.parse(editTextdateNaissanceReg.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(Checkform(NameFirstNameUser,UserName, Ville, DateNaissance)){
                    Intent intentWelcom = new Intent(ezeety_registration_second.this, ezeety_welcome.class);
                    intentWelcom.putExtra("UserNameFirstNameReg",NameFirstNameUser);
                    intentWelcom.putExtra("UserNameReg", UserName);
                    intentWelcom.putExtra("VilleReg", Ville);
                    intentWelcom.putExtra("DateNaissance", DateNaissance);
                    startActivity(intentWelcom);
                } return;

            }
        });
    }

    private boolean Checkform(String nameFirstNameUser, String userName, String ville, Date dateNaissance) {
        if(nameFirstNameUser == null || nameFirstNameUser.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre Nom et prenom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName == null || userName.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser le nom d'utilisateur", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ville == null || ville.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre Nom Ville", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateNaissance == null){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre date de naissance ou date invalide", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
