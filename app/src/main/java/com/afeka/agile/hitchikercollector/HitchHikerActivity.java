package com.afeka.agile.hitchikercollector;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
                String Name = ((EditText) (findViewById(R.id.Name))).getText().toString();
                TextView Output = findViewById(R.id.Output);
                if (!((CheckBox) findViewById(R.id.CurrentLocation)).isChecked() && Starting_Point.isEmpty() || Destination_Point.isEmpty() || Name.isEmpty())
                    Output.setText("Missing input!");
                else {
                    Output.setText("Hello " + Name + "! \nYou want to go from " + Starting_Point + " to " + Destination_Point + "...\n");
                    try {
                        if (((CheckBox) findViewById(R.id.CurrentLocation)).isChecked())
                            Starting_Point = LocationUtil.latLngToString(LocationUtil.getCurrentLocation());
                        else
                            Starting_Point = LocationUtil.addressToLatLngStr(Starting_Point);
                        Destination_Point = LocationUtil.addressToLatLngStr(Destination_Point).toString();

                    } catch (Exception NullPointerException) {
                        Output.setText("Locations not found");
                    }
                }
            }
        });
    }

    }
