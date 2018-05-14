package com.afeka.agile.hitchikercollector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button OK_button = findViewById(R.id.OK_button);
        OK_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Starting_Point = ((EditText) (findViewById(R.id.Starting_Point))).getText().toString();
                String Destination_Point = ((EditText) (findViewById(R.id.Destination_Point))).getText().toString();
                TextView Output = findViewById(R.id.Output);
                if (Starting_Point.isEmpty() || Destination_Point.isEmpty())
                    Output.setText("Missing input!");
                else
                    Output.setText("Driving from " + Starting_Point + " to " + Destination_Point + "...\nHave a nice ride!");
                if (Starting_Point.equals(getResources().getString(R.string.current_location)))
                    Starting_Point=getCurrentLocation();
                else
                    Starting_Point=getLatLngFromAddress(Starting_Point).toString();
                Destination_Point=getLatLngFromAddress(Destination_Point).toString();
                String collectPoints = getCollectPoints();
                String uri = "https://www.google.com/maps/dir/?api=1&origin=" + Starting_Point + "&destination=" + Destination_Point + "&travelmode=driving&waypoints=" + collectPoints;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

    }

    private String getCollectPoints() {
        String collectPoint = getLatLngFromAddress("Beit Yanai,Israel") + "%7C" + getLatLngFromAddress("Netanya,Israel");
        return collectPoint;
    }

    public String getLatLngFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            return address.get(0).getLatitude()+"," +address.get(0).getLongitude();
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LatLng currentLocation = null;
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
        } else {
            if (isGPSEnabled) {
                Log.d("activity", "RLOC: GPS Enabled");
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "RLOC: loc by GPS");
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            }
            if (isNetworkEnabled && currentLocation == null) {
                Log.d("activity", "LOC Network Enabled");
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "LOC by Network");
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        }
        return currentLocation.latitude+","+currentLocation.longitude;
    }


}
