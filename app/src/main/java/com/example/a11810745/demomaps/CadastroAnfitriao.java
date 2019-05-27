package com.example.a11810745.demomaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11810745.demomaps.Models.Anfitriao;


public class CadastroAnfitriao extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean latLngPreenchido;
    private String latitude, longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_anfitriao);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String[] localidade = data.getStringArrayExtra("localidade");
            if (localidade != null && localidade.length == 2) {
                latLngPreenchido = true;
                latitude = localidade[0];
                longitude = localidade[1];
            }
        }
    }

    public void escolherLocalidade(View view) {
        Intent intent = new Intent(this, MarkerAdd.class);
        startActivityForResult(intent, 123);
    }

    public void enviarCadastro(View view){
        EditText c_mailAnfi = (EditText) findViewById(R.id.mailAnfi);
        EditText c_senAnfi = (EditText) findViewById(R.id.senAnfi);
        EditText c_nomAnfi = (EditText) findViewById(R.id.nomeAnfi);
        EditText c_cpfAnfi = (EditText) findViewById(R.id.cpfAnfi);

        String mailAnfi = c_mailAnfi.getText().toString();
        String senAnfi = c_senAnfi.getText().toString();
        String nomAnfi = c_nomAnfi.getText().toString();
        String cpfAnfi= c_cpfAnfi.getText().toString();

        if (!latLngPreenchido) {
            // erro!
            return;
        }
        if(mailAnfi == null){
            return;
        }
        if(senAnfi == null){
            return;
        }
        if(nomAnfi == null){
            return;
        }
        if(cpfAnfi == null){
            return;
        }

        Anfitriao anfitriao = new Anfitriao();
        anfitriao.nomAnfi = nomAnfi;
        anfitriao.mailAnfi = mailAnfi;
        anfitriao.cpfAnfi = cpfAnfi;
        anfitriao.senAnfi = senAnfi;
        anfitriao.latAnfi = latitude;
        anfitriao.lngAnfi = longitude;

        RequisicaoJson.Callback<Void> callback = new RequisicaoJson.Callback<Void>() {
            @Override
            public void concluido(int status, Void objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                if (status == 204) {
                    Toast.makeText(CadastroAnfitriao.this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    // OK!
                    Intent LoggedIn = new Intent(CadastroAnfitriao.this, InitialActivity.class);
                    CadastroAnfitriao.this.startActivity(LoggedIn);
                } else {
                    Toast.makeText(CadastroAnfitriao.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        };

        RequisicaoJson.post(callback, "http://10.12.189.253:3000/api/anfitriao/criar", Void.class, anfitriao);


    }
}
