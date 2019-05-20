package com.example.a11810745.demomaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private boolean mapReady, permissaoOk;
    private Location ultimaLocation;
    private ProgressDialog progressDialog;
    private static final LatLng Local1 = new LatLng(-23.583797, -46.645840);
    private static final LatLng Local2 = new LatLng(-23.587605, -46.650098);
    private static final LatLng Local3 = new LatLng(-23.588183, -46.640666);



    // Evento evento =  new Evento();
    // Localidade loca =  new Localidade();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // @@@

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);

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


    @SuppressLint("MissingPermission")
    private void inicializaMapa() {
        if (!permissaoOk || !mapReady) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

    }

    private void permissaoGpsOk() {
        permissaoOk = true;
        inicializaMapa();
    }

    public void Filtro(View view) {
        // vai pra pagina de filtro
        Intent go = new Intent(getApplicationContext(), Filter.class);
        startActivity(go);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mapReady = true;
        inicializaMapa();

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
        Canvas canvas = new Canvas(bmp);

        Marker place1 = mMap.addMarker(new MarkerOptions()
            .position(Local1)
            .title("Local 1")
            .snippet("Musica ao Vivo")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker2)));

        Marker place2 = mMap.addMarker(new MarkerOptions()
                .position(Local2)
                .title("Local 2")
                .snippet("Stand-Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker3)));

        Marker place3 = mMap.addMarker(new MarkerOptions()
                .position(Local3)
                .title("Local 3")
                .snippet("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin eget purus a enim lacinia imperdiet. Vestibulum ut maximus leo, vitae.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmarker1)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions != null &&
                    permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissaoGpsOk();
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(this, "Erro!", Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
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

}
