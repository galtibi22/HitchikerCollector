package com.afeka.agile.hitchikercollector;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import utils.DBUtil;
import utils.LocationUtil;

public class HitchHikerActivity extends AppCompatActivity {
    LocationUtil locationUtil;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitchhiker);

        Button OK_button = findViewById(R.id.OK_button);
        OK_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Starting_Point = ((EditText) (findViewById(R.id.Sourse))).getText().toString();
                String Destination_Point = ((EditText) (findViewById(R.id.Destination))).getText().toString();
                LatLng startingPoint;
                LatLng destinationPoint;
                String Name = ((EditText) (findViewById(R.id.Name))).getText().toString();
                TextView Output = findViewById(R.id.Output);
                if (!((CheckBox) findViewById(R.id.CurrentLocation)).isChecked() && Starting_Point.isEmpty() || Destination_Point.isEmpty() || Name.isEmpty())
                    Output.setText("Missing input!");
                else {
                    if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                        Starting_Point="Current Location";
                    Output.setText("Hello " + Name + "! \nYou want to go from " + Starting_Point + " to " + Destination_Point + "...\n");
                    try {
                        if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                            startingPoint = LocationUtil.getCurrentLocation();
                        else
                            startingPoint = LocationUtil.addressToLatLng(Starting_Point);
                        destinationPoint = LocationUtil.addressToLatLng(Destination_Point);
                        LatLng s=new LatLng(startingPoint.latitude,startingPoint.longitude);
                        LatLng d=new LatLng(destinationPoint.latitude,destinationPoint.longitude);

                        DBUtil.writeToDB(Name, s, d);

                    } catch (Exception NullPointerException) {
                        Output.setText("Locations not found");
                    }
                }
            }
        });
    }

}
