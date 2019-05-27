package com.example.a11810745.demomaps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.a11810745.demomaps.Models.*;

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
        final Anfitriao anfitriao = new Anfitriao();
         EditText e_mailAnfi = (EditText) findViewById(R.id.mailAnfi);
         EditText e_senAnfi = (EditText) findViewById(R.id.senAnfi);

         anfitriao.mailAnfi = e_mailAnfi.getText().toString();
         anfitriao.senAnfi = e_senAnfi.getText().toString();

         RequisicaoJson.Callback<Anfitriao> callback = new RequisicaoJson.Callback<Anfitriao>() {

             @Override
             public void concluido(int status, Anfitriao objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                 progressDialog.dismiss();

                 //Autenticar a semelhanca dos objetos registrados e os do banco
                 if (objetoRetornado != null) {
                     if (objetoRetornado.senAnfi.equals(anfitriao.senAnfi)) {
                         Intent LoggedIn = new Intent(Inicial_Anfi.this, MainActivity.class);
                         Inicial_Anfi.this.startActivity(LoggedIn);
                     }  else {
                         //Toast.makeText(this, "E-mail/senha incorreta!", Toast.LENGTH_LONG).show();
                        }

                 }
             }
         };



        progressDialog.show();

            RequisicaoJson.get(callback,"http://10.12.189.253:3000/api/anfitriao/obter?mailAnfi="+anfitriao.mailAnfi,Anfitriao.class);



         }




    //CADASTRO SEM VALIDACAO
    public void Cadastro(View view) {
        // vai pra activity do mapa
        Intent go = new Intent(getApplicationContext(), CadastroAnfitriao.class);
        startActivity(go);

    }
}
