package com.example.a11810745.demomaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.Anfitriao;
import com.example.a11810745.demomaps.Models.Convidado;

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

    }
    public void enviarCadastro(View view){

        EditText c_nomeConv= (EditText) findViewById(R.id.nomeConv);

        Spinner c_genConv= (Spinner) findViewById(R.id.genConv);
        EditText c_mailConv= (EditText) findViewById(R.id.mailConv);
        EditText c_senConv = (EditText)findViewById(R.id.senConv);

        String nomeConv = c_nomeConv.getText().toString();
        Object sel = c_genConv.getSelectedItem();
        String genConv = (sel == null ? "" : sel.toString());
        String mailConv = c_mailConv.getText().toString();
        String senConv = c_senConv.getText().toString();




        if(nomeConv == null){
            return;
        }
        if(mailConv == null){
            return;
        }
        if(senConv == null){
            return;
        }
        if(genConv == null){
            return;
        }


        Convidado convidado = new Convidado();
        convidado.nomeConv=nomeConv;
        convidado.mailConv=mailConv;
        convidado.senConv=senConv;
        convidado.genConv=genConv;

        RequisicaoJson.Callback<Void> callback = new RequisicaoJson.Callback<Void>() {
            @Override
            public void concluido(int status, Void objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                if (status == 204) {
                    // OK!
                } else {
                    Toast.makeText(CadastroConvidado.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        };
        RequisicaoJson.post(callback, "https://10.12.189.253/api/convidado/criar", Void.class, convidado);
    }
}
