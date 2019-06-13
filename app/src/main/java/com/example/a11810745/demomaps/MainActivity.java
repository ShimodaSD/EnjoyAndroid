package com.example.a11810745.demomaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a11810745.demomaps.Models.Evento;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener, DialogInterface.OnDismissListener, GoogleMap.OnMapClickListener{

    public static final int MY_LOCATION_REQUEST_CODE = 1;
    public GoogleMap mMap;
    public boolean mapReady, permissaoOk;
    public Location ultimaLocation;
    public ProgressDialog progressDialog;
    public static final LatLng Local1 = new LatLng(-23.565112, -46.694912);
    public static final LatLng Local2 = new LatLng(-23.587605, -46.650098);
    public static final LatLng Local3 = new LatLng(-23.582298, -46.644076);
    private boolean foiClicado = false;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapReady = false;
        permissaoOk = false;

        //checando a versao do dispositivo atual
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, MY_LOCATION_REQUEST_CODE);
                return;
            }
        }

        permissaoGpsOk();

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
        switch (item.getItemId()){
            case R.id.homes:
                Intent LoggedIn = new Intent(this, MainActivity.class);
                this.startActivity(LoggedIn);
                break;
            case R.id.meuperfil:
                break;
            case R.id.meuseventos:
                Intent LoggedInn = new Intent(this, CadastroDeEventos.class);
                this.startActivity(LoggedInn);
                break;
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    private void inicializaMapa() {
        if (!permissaoOk || !mapReady) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

    }

    private void permissaoGpsOk() {
        permissaoOk = true;
        inicializaMapa();
    }

    public void filtro(View view) {
        LinearLayout activity_layout = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_filter, null);
        ListView list  = (ListView) activity_layout.findViewById(R.id.Filter);
        EditText search = (EditText) activity_layout.findViewById(R.id.FilterSearch);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Teatro");
        categories.add("Stand-up");
        categories.add("Festa");
        categories.add("Música ao Vivo");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        list.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(activity_layout);
        builder.setTitle("Localizar evento");
        builder.setCancelable(true);
        builder.setOnDismissListener(this);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
            // vai pra pagina de filtro
            //Intent go = new Intent(getApplicationContext(), Filter.class);
            //startActivity(go);

          }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mapReady = true;
        inicializaMapa();
        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(-23.587373, -46.644721), 12);
        map.moveCamera(point);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
        Canvas canvas = new Canvas(bmp);

        /*RequisicaoJson.Callback<Evento[]> callback = new RequisicaoJson.Callback<Evento[]>() {
            @Override
            public void concluido(int status, Evento[] objetoRetornado, String stringErro, Exception excecaoOcorrida) {
                if (objetoRetornado != null) {
                    Evento evento = new Evento();
                    evento.


                } else {

                }
            }
        };
        RequisicaoJson.get(callback, "http://10.12.189.253:3000/api/evento/listar", Evento[].class );
*/

        Marker place1 = mMap.addMarker(new MarkerOptions()
                        .position(Local1)
                        .title("Z")
                        .snippet("Musica ao Vivo")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker2)));
                //place1.setTag(array[i]);

                Marker place2 = mMap.addMarker(new MarkerOptions()
                        .position(Local2)
                        .title("Central da Comédia")
                        .snippet("Stand-Up")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker3)));
                //place2.setTag(array[i]);

                Marker place3 = mMap.addMarker(new MarkerOptions()
                        .position(Local3)
                        .title("SESC Vila Mariana")
                        .snippet("Teatro")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker1)));
                //place3.setTag(array[i]);




    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Você está aqui!", Toast.LENGTH_SHORT).show();
        // camera vai pra posiçao atual do usuario
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Localização Atual:\n" + location, Toast.LENGTH_LONG).show();
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onMarkerClick(Marker marker) {
        //double click pra abrir dialog
        if(foiClicado == true) {

            //Evento objeto = (Evento)marker.getTag();

            LinearLayout dialogmarker = (LinearLayout)getLayoutInflater().inflate(R.layout.dialogmarker, null);
            TextView nomEven =  dialogmarker.findViewById(R.id.nomEven);
            nomEven.setText("  Z");
            TextView tipoEven = dialogmarker.findViewById(R.id.tipoEven);
            tipoEven.setText("  Música ao vivo");
            TextView descEven = dialogmarker.findViewById(R.id.descEven);
            descEven.setText("  De quinta a sábado, atrações de rock, jazz, blues, folk, samba e reggae apresentam-se no salão decorado com ganchos de pendurar carne no teto e quadrinhos diversos na parede.");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogmarker);
            builder.setTitle("Preview");
            builder.setCancelable(true);
            builder.setOnDismissListener(this);

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            foiClicado = false;




        }
        else {
            foiClicado = true;
        }


        return false;
    }
    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onLocationChanged(Location location) {
        progressDialog.dismiss();
        ultimaLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
