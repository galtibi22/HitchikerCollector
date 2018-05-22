package com.afeka.agile.hitchikercollector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import utils.LocationUtil;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    LocationUtil locationUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions();
        locationUtil=new LocationUtil(this);
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
                    Starting_Point=locationUtil.getCurrentLocation();
                else
                    Starting_Point=locationUtil.getLatLngFromAddress(Starting_Point).toString();
                Destination_Point=locationUtil.getLatLngFromAddress(Destination_Point).toString();
                String collectPoints = getCollectPoints();
                String uri = "https://www.google.com/maps/dir/?api=1&origin=" + Starting_Point + "&destination=" + Destination_Point + "&travelmode=driving&waypoints=" + collectPoints;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

    }

    private String getCollectPoints() {
        String collectPoint =locationUtil.getLatLngFromAddress("Beit Yanai,Israel") + "%7C" + locationUtil.getLatLngFromAddress("Netanya,Israel");
        return collectPoint;
    }

    private void permissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }



}
