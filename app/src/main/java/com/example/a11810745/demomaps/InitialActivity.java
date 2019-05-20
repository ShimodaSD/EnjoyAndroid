package com.example.a11810745.demomaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.a11810745.demomaps.R;


public class InitialActivity extends AppCompatActivity {

    private double user = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_conv:
                if (checked)
                    user = 1;
                    break;
            case R.id.radio_anf:
                if (checked)
                    user = 2;
                    break;
        }
    }

    public void EntrarGeral(View view) {
        //user true -> activity do convidado
        if(user == 1) {
            Intent go = new Intent(getApplicationContext(), Inicial_Conv.class);
            startActivity(go);
        }
        // user false -> activity do convidado
        else if (user == 2) {
            Intent go = new Intent(getApplicationContext(), Inicial_Anfi.class);
            startActivity(go);
        }
    }
}
