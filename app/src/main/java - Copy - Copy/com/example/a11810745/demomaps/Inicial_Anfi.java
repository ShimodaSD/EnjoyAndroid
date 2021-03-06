package com.example.a11810745.demomaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.Anfitriao;
import com.example.a11810745.demomaps.Models.Convidado;

import java.util.Map;

public class Inicial_Anfi extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial_anfi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    //LOGIN DO ANFITRIAO
    public void Entrar(View view) {
        RequisicaoJson.Callback<Anfitriao> callback = new RequisicaoJson.Callback<Anfitriao>() {

            @Override
            public void concluido(int status, Anfitriao objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                progressDialog.dismiss();

                // edit text to string
                EditText e_mailAnfi = (EditText) findViewById(R.id.mailAnfi);
                EditText e_senAnfi = (EditText) findViewById(R.id.senAnfi);

                String mailAnfi = e_mailAnfi.getText().toString();
                String senAnfi = e_senAnfi.getText().toString();

                //Autenticar a semelhanca dos objetos registrados e os do banco
                //if(objetoRetornado.mailAnfi == mailAnfi && objetoRetornado.senAnfi == senAnfi){
                if(senAnfi != null){ //testar sem nada
                    // goes to next activity
                    Intent LoggedIn = new Intent(Inicial_Anfi.this, MapsActivity.class);
                    Inicial_Anfi.this.startActivity(LoggedIn);
                }
            }
        };

        progressDialog.show();

        RequisicaoJson.get(callback, "COLOCAR O NOME DO SERVER CORRETO AQUI", Anfitriao.class);
    }
}
