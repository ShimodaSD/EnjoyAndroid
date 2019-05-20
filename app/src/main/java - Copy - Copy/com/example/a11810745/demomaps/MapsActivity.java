package com.example.a11810745.demomaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

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

    // Evento evento =  new Evento();
    // Localidade loca =  new Localidade();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
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
    private void atualizarLocation() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !locationManager.isLocationEnabled()) || !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "Por favor, habilite o GPS para poder continuar!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Por favor, aguarde. Obtendo sua localização...");
            progressDialog.show();
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, Looper.getMainLooper());
        } catch (Exception ex) {
            Toast.makeText(this, "Falha ao acessar o GPS!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void inicializaMapa() {
        if (!permissaoOk || !mapReady) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        atualizarLocation();
    }

    private void permissaoGpsOk() {
        permissaoOk = true;
        inicializaMapa();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mapReady = true;
        inicializaMapa();
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
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // camera vai pra posiçao atual do usuario
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
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
