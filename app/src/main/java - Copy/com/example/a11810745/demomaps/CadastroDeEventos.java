package com.example.a11810745.demomaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class CadastroDeEventos extends AppCompatActivity {

    private ArrayList<String> endes;
    private SpinnerAdapter idEndeAdapter;
    private Spinner idEnde;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_evento);

        endes = new ArrayList<>();
        endes.add("Item a");
        endes.add("Item B");

        idEndeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, endes);

        idEnde = findViewById(R.id.idEnde);
        idEnde.setAdapter(idEndeAdapter);


    }
}