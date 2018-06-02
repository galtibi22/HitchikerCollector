package com.afeka.agile.hitchikercollector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import utils.HitchhikerManager;
import utils.LocationUtil;

public class DriverActivity extends AppCompatActivity {

    HitchhikerManager hitchhikerManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hitchhikerManager=new HitchhikerManager();
        Button OK_button = findViewById(R.id.OK_button);
        OK_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Starting_Point = ((EditText) (findViewById(R.id.Starting_Point))).getText().toString();
                String Destination_Point = ((EditText) (findViewById(R.id.Destination_Point))).getText().toString();
                LatLng startingPoint;
                LatLng destinationPoint;
                TextView Output = findViewById(R.id.Output);
                if (!((CheckBox) findViewById(R.id.CurrentLocation)).isChecked() && Starting_Point.isEmpty() || Destination_Point.isEmpty())
                    Output.setText("Missing input!");
                else {
                    Output.setText("Driving from " + Starting_Point + " to " + Destination_Point + "...\nHave a nice ride!");
                    if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                        startingPoint = LocationUtil.getCurrentLocation();
                    else
                        startingPoint = LocationUtil.addressToLatLng(Starting_Point);
                    destinationPoint = LocationUtil.addressToLatLng(Destination_Point);
                    String collectPoints = hitchhikerManager.getHitchhikers(startingPoint, destinationPoint);
                    Starting_Point = LocationUtil.latLngToString(startingPoint);
                    Destination_Point = LocationUtil.latLngToString(destinationPoint);
                    String uri = "https://www.google.com/maps/dir/?api=1&origin=" + Starting_Point + "&destination=" + Destination_Point + "&travelmode=driving&waypoints=" + collectPoints;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            }
        });

    }




}
