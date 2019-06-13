package com.example.a11810745.demomaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.Anfitriao;
import com.example.a11810745.demomaps.Models.Categoria;
import com.example.a11810745.demomaps.Models.Evento;

import java.util.ArrayList;

public class CadastroDeEventos extends AppCompatActivity {

    private SpinnerAdapter idCategoriasAdapter;
    private Spinner idCategoria;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_evento);

        RequisicaoJson.Callback<Categoria[]> callback = new RequisicaoJson.Callback<Categoria[]>() {
            @Override
            public void concluido(int status, Categoria[] objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                if (objetoRetornado != null) {
                    // OK!
                    idCategoriasAdapter = new ArrayAdapter<>(CadastroDeEventos.this, android.R.layout.simple_spinner_dropdown_item, objetoRetornado);
                    idCategoria = findViewById(R.id.idCate);
                    idCategoria.setAdapter(idCategoriasAdapter);
                } else {
                    Toast.makeText(CadastroDeEventos.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        };
        RequisicaoJson.get(callback, "http://10.12.189.253:3000/api/categoria/listar", Categoria[].class);


    }
    public void enviarCadastro(){

        EditText c_nomeEven = (EditText) findViewById(R.id.nomeEven);
        EditText c_dataEven = (EditText) findViewById(R.id.dataEven);
        EditText c_horaEven = (EditText) findViewById(R.id.horaEven);
        Spinner c_idCate = (Spinner) findViewById(R.id.idCate);

        String nomeEven = c_nomeEven.getText().toString();
        String dataEven = c_dataEven.getText().toString();
        String horaEven= c_horaEven.getText().toString();
        int idAnfi = Anfitriao.anfitriaoLogado.idAnfi;
        int idCate = (int) c_idCate.getSelectedItem();
        double latEven = Anfitriao.anfitriaoLogado.latAnfi;
        double lngEven = Anfitriao.anfitriaoLogado.lngAnfi;


        if(nomeEven == null){
            return;
        }
        if(dataEven == null){
            return;
        }
        if(horaEven == null){
            return;
        }

        Evento evento = new Evento();
        evento.nomeEnve = nomeEven;
        evento.dataEven = dataEven;
        evento.horaEven = horaEven;
        evento.idAnfi = idAnfi;
        evento.latEven = latEven;
        evento.lngEven = lngEven;
        evento.idCate = idCate;
        RequisicaoJson.Callback<Void> callback = new RequisicaoJson.Callback<Void>() {
            @Override
            public void concluido(int status, Void objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                if (status == 204) {
                    Toast.makeText(CadastroDeEventos.this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    // OK!
                    Intent LoggedIn = new Intent(CadastroDeEventos.this, InitialActivity.class);
                    CadastroDeEventos.this.startActivity(LoggedIn);
                } else {
                    Toast.makeText(CadastroDeEventos.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        };

        RequisicaoJson.post(callback, "http://10.12.189.253:3000/api/evento/criar",Void.class,Evento.class);
    }
}