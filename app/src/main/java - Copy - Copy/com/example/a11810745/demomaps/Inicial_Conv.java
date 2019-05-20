package com.example.a11810745.demomaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.Convidado;
import com.example.a11810745.demomaps.Models.Convidado;

public class Inicial_Conv extends AppCompatActivity {

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

    //LOGIN DO Convidado
    public void entrar(View view) {
        RequisicaoJson.Callback<Convidado> callback = new RequisicaoJson.Callback<Convidado>() {

            @Override
            public void concluido(int status, Convidado objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                progressDialog.dismiss();

                // edit text to string
                EditText e_mailConv = (EditText) findViewById(R.id.mailConv);
                EditText e_senConv = (EditText) findViewById(R.id.senConv);

                String mailConv = e_mailConv.getText().toString();
                String senConv = e_senConv.getText().toString();

                //Autenticar a semelhanca dos objetos registrados e os do banco
                //if(objetoRetornado.mailAnfi == mailAnfi && objetoRetornado.senAnfi == senAnfi){
                if(senConv != null){ //testar sem nada
                    // goes to next activity
                    Intent LoggedIn = new Intent(Inicial_Conv.this, MapsActivity.class);
                    Inicial_Conv.this.startActivity(LoggedIn);
                }
            }
        };

        progressDialog.show();

        RequisicaoJson.get(callback, "COLOCAR O NOME DO SERVER CORRETO AQUI", Convidado.class);
    }
}
