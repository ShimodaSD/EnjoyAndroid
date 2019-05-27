package com.example.a11810745.demomaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.a11810745.demomaps.R;


public class InitialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }



    public void EntrarGeral(View view) {
        //user true -> activity do convidado
        RadioButton radio_conv = findViewById(R.id.radio_conv);
        RadioButton radio_anf = findViewById(R.id.radio_anf);
        if(radio_conv.isChecked()) {
            Intent go = new Intent(getApplicationContext(), Inicial_Conv.class);
            startActivity(go);
        }
        // user false -> activity do convidado
        else if (radio_anf.isChecked()) {
            Intent go = new Intent(getApplicationContext(), Inicial_Anfi.class);
            startActivity(go);
        }
    }
}
