package com.example.a11810745.demomaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.*;

public class Inicial_Conv extends AppCompatActivity {

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial_conv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Aguarde...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


    }

    //LOGIN DO Convidado
    public void Entrar(View view) {

        final Convidado convidado = new Convidado();
        EditText e_mailConv = (EditText) findViewById(R.id.mailConv);
        EditText e_senConv = (EditText) findViewById(R.id.senConv);

        convidado.mailConv = e_mailConv.getText().toString();
        convidado.senConv = e_senConv.getText().toString();

        RequisicaoJson.Callback<Convidado> callback = new RequisicaoJson.Callback<Convidado>() {

            @Override
            public void concluido(int status, Convidado objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                progressDialog.dismiss();

                //Autenticar a semelhanca dos objetos registrados e os do banco
                if (objetoRetornado != null) {
                    if (objetoRetornado.senConv.equals(convidado.senConv) ){
                        Intent LoggedIn = new Intent(Inicial_Conv.this, MainActivity.class);
                        Inicial_Conv.this.startActivity(LoggedIn);

                    }
                    else {
                        Toast.makeText(Inicial_Conv.this, "E-mail/senha incorreta!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Inicial_Conv.this, "Erro!", Toast.LENGTH_LONG).show();
                }
            }
        };

        progressDialog.show();

        RequisicaoJson.get(callback, "http://10.12.189.253:3000/api/convidado/obter?mailConv=" + convidado.mailConv, Convidado.class);
        // Intent go = new Intent(getApplicationContext(), MainActivity.class);
        // startActivity(go);
    }


    public void Cadastro(View view) {
        // vai pra activity do mapa
        Intent go = new Intent(getApplicationContext(), CadastroConvidado.class);
        startActivity(go);

    }
}

