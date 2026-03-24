package com.example.app1122;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Women extends AppCompatActivity {

    TextView txtla;
    TextView txtshowinfo;
    Button btnn;

    FirebaseDatabase database;
    DatabaseReference myRef;

    LocationManager locationManager;
    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Half-Project");

        txtla = findViewById(R.id.txtlat);
        txtshowinfo = findViewById(R.id.txtloc);
        btnn = findViewById(R.id.btn);

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("msg").setValue(1);
                Toast.makeText(getApplicationContext(), "Help Me!!!", Toast.LENGTH_LONG).show();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String latt = String.valueOf(location.getLatitude());
                String lot = String.valueOf(location.getLongitude());

                txtshowinfo.setText("Latitude " + latt);
                txtla.setText("Longitude " + lot);

                myRef.child("lat").setValue(Double.valueOf(latt));
                myRef.child("lot").setValue(Double.valueOf(lot));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent inn = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(inn);
            }
        };

        getloc();
    }

    private void getloc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, listener);
    }
}
