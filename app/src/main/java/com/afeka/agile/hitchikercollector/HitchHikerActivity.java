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
import android.support.v7.app.AppCompatActivity;

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
                if (Starting_Point.isEmpty() || Destination_Point.isEmpty() || Name.isEmpty())
                    Output.setText("Missing input!");
                else
                    Output.setText("Hello " + Name + "! \nYou want to go from " + Starting_Point + " to " + Destination_Point + "...\n");
                try {
                    if (Starting_Point.equals(getResources().getString(R.string.current_location)))
                        Starting_Point = locationUtil.getCurrentLocation();
                    else
                        Starting_Point = locationUtil.getLatLngFromAddress(Starting_Point).toString();
                    Destination_Point = locationUtil.getLatLngFromAddress(Destination_Point).toString();

                } catch (Exception NullPointerException) {
                    Output.setText("Locations not found");
                }
            }
        });
    }
        private String getCollectPoints() {
            String collectPoint =locationUtil.getLatLngFromAddress("Beit Yanai,Israel") + "%7C" + locationUtil.getLatLngFromAddress("Netanya,Israel");
            return collectPoint;
        }

    }
