package com.example.a11810745.demomaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


public class CadastroConvidado extends AppCompatActivity  {
    private Spinner genConv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_convidado);

        genConv = (Spinner) findViewById(R.id.genConv);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.generos,android.R.layout.simple_spinner_dropdown_item);
        genConv.setAdapter(adapter);


       /* Conv = new ArrayList<>();
        Conv.add("Selecione :");
        Conv.add("Masculino");
        Conv.add("Feminino");
        Conv.add("Outro");

        genConvAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Conv);

        genConv = findViewById(R.id.genConv);
        genConv.setAdapter(genConvAdapter);
        */
    }
    public void enviarCadastro(){

    }
}
