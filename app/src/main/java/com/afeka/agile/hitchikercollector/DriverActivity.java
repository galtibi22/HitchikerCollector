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
import utils.HttpClient;
import utils.LocationUtil;

public class DriverActivity extends AppCompatActivity {

    HitchhikerManager hitchhikerManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        hitchhikerManager=new HitchhikerManager();
        Button OK_button = findViewById(R.id.OK_button);
        OK_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] Starting_Point = {((EditText) (findViewById(R.id.Starting_Point))).getText().toString()};
                final String[] Destination_Point = {((EditText) (findViewById(R.id.Destination_Point))).getText().toString()};
                final LatLng[] startingPoint = new LatLng[1];
                final LatLng[] destinationPoint = new LatLng[1];
                TextView Output = findViewById(R.id.Output);
                if (!((CheckBox) findViewById(R.id.CurrentLocation)).isChecked() && Starting_Point[0].isEmpty() || Destination_Point[0].isEmpty())
                    Output.setText("Missing input!");
                else {
                    if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                        Starting_Point[0]="Current Location";
                    Output.setText("Driving from " + Starting_Point[0] + " to " + Destination_Point[0] + "...\nHave a nice ride!");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                                    startingPoint[0] = LocationUtil.getCurrentLocation();
                                else
                                    startingPoint[0] = LocationUtil.addressToLatLng(Starting_Point[0]);
                                destinationPoint[0] = LocationUtil.addressToLatLng(Destination_Point[0]);
                                String collectPoints = hitchhikerManager.getHitchhikers(startingPoint[0], destinationPoint[0]);
                                Starting_Point[0] = LocationUtil.latLngToString(startingPoint[0]);
                                Destination_Point[0] = LocationUtil.latLngToString(destinationPoint[0]);
                                String uri = "https://www.google.com/maps/dir/?api=1&origin=" + Starting_Point[0] + "&destination=" + Destination_Point[0] + "&travelmode=driving&waypoints=" + collectPoints;
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                startActivity(intent);} catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                }
            }
        });

    }




}
