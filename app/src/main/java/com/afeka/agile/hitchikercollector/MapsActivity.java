package com.afeka.agile.hitchikercollector;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //String Starting_Point = getIntent().getStringExtra("START");
    //String Destination_Point = getIntent().getStringExtra("DESTINATION");

    //Coordinates startCo = new Coordinates(Starting_Point, 100, 100);
    //Coordinates endCo = new Coordinates(Destination_Point, 200, 200);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng Mark1 = new LatLng(startCo.getLatitude(), startCo.getLongitude());
        //LatLng Mark2 = new LatLng(endCo.getLatitude(), endCo.getLongitude());
        LatLng Mark1 = new LatLng(100, 100);
        LatLng Mark2 = new LatLng(200, 200);
        mMap.addMarker(new MarkerOptions().position(Mark1).title("Marker 1"));
        mMap.addMarker(new MarkerOptions().position(Mark2).title("Marker 2"));
        mMap.addPolyline(new PolylineOptions()
                .add(Mark1,Mark2)
                .width(5)
                .color(Color.RED));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mark1));


    }
}
