package com.afeka.agile.hitchikercollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button OK_button = findViewById(R.id.OK_button);
        OK_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fsa   EditText Starting_Point = findViewById(R.id.Starting_Point);
                //    1EditText Destination_Point = findViewById(R.id.Destination_Point);
                String Starting_Point = ((EditText)(findViewById(R.id.Starting_Point))).getText().toString();
                String Destination_Point = ((EditText)(findViewById(R.id.Destination_Point))).getText().toString();
                TextView Output = findViewById(R.id.Output);
                if(Starting_Point.isEmpty() || Destination_Point.isEmpty())
                    Output.setText("Missing input!");
                else {
                    Output.setText("Driving from " + Starting_Point + " to " + Destination_Point + "...\nHave a nice ride!");
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    //intent.putExtra("START", Starting_Point);
                    //intent.putExtra("DESTINATION", Destination_Point);
                    startActivity(intent);
                }
            }
        });

    }
}
