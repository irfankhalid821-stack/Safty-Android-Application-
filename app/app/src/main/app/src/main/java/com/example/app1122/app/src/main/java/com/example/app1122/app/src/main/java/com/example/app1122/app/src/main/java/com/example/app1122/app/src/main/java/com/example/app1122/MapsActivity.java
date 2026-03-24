package com.example.app1122;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database2;
    DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        database2 = FirebaseDatabase.getInstance();
        myRef2 = database2.getReference("uni");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double lat = dataSnapshot.child("lat").getValue(Double.class);
                Double lot = dataSnapshot.child("ll").getValue(Double.class);
                if (lat == null || lot == null) {
                    return;
                }

                LatLng point = new LatLng(lat, lot);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point).title("Tracked User"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
