package com.lawriecate.apps.nutrifit;

import android.Manifest;
import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ulises on 28/10/2016.
 */
//AIzaSyDcAYCRNNKp0S8-69zOTsuxMu2Pjv1-W04 Clave API para Maps
public class MenuMapas extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Objeto para manejar API de Google Services
    private GoogleApiClient cliente;
    Location mLastLocation;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lawriecate.apps.nutrifit.R.layout.activity_mapas);
        setMap();
        if (cliente == null) {
            cliente = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).addApi(AppIndex.API).build();
        }
}

    protected void onStart() {
        super.onStart();
        cliente.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cliente.disconnect();
    }

    private void setMap() {
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(com.lawriecate.apps.nutrifit.R.id.plantilla_maps, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(cliente);
        if (mLastLocation != null){
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("I'm here!!!"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8);
            googleMap.moveCamera(cameraUpdate);
        } else {


        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(MenuMapas.this, "Error en conexión", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(MenuMapas.this, "Error en conexión", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MenuMapas.this, "Error en conexión", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        try {
            String jsonFile = getJSONFile("places.json", getBaseContext());    //Obtener datos de JSON en String
            JSONObject jsonObject = new JSONObject(jsonFile);   //Instancia de JSONObject con datos de archivo places.json
            JSONArray jsonArray = jsonObject.getJSONArray("lugares_ejercicio");  //Obtener arreglo de places.json, tiene 10 elementos
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i); //Obtener objeto i de arreglo
                JSONObject city = object.getJSONObject("place"); //Obtener objeto city de objeto i
                String nombre = city.getString("name");
                Double latitud = city.getDouble("latitude");
                Double longitud = city.getDouble("longitude");

                LatLng latLng = new LatLng(latitud, longitud);
                googleMap.addMarker(new MarkerOptions().position(latLng).title(nombre));
                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);    //Hacer zoom en pantalla de lugar identificado
                //googleMap.animateCamera(cameraUpdate);

            }
            LatLng latLng2 = new LatLng(19.284056,-99.135926);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng2, 14);
            googleMap.moveCamera(cameraUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void moveToCurrentLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
            googleMap.moveCamera(cameraUpdate);
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
    }

    public static String getJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }


}
