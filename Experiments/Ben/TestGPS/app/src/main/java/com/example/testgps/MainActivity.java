package com.example.testgps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.testGPS.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 42;
    //Text discriptions of data from GPS in the UI
    TextView tv_Lat, tv_Long, tv_Alt, tv_Acc, tv_Speed, tv_Update, tv_Sensor;
    //Switches to remember if we are tracking location or not,
    //and to switch between high performance and battery saver
    Switch s_Update, s_BattSaver;

    //variables to set the default and fast update intervals
    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;

    boolean updateOn = false;

    //Location request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    //Google's API for location services the app lives off this G
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Lat = findViewById(R.id.tvLat);
        tv_Long = findViewById(R.id.tvLong);
        tv_Alt = findViewById(R.id.tvAlt);
        tv_Acc = findViewById(R.id.tvAcc);
        tv_Speed = findViewById(R.id.tvSpeed);
        tv_Update = findViewById(R.id.tvUpdate);
        tv_Sensor = findViewById(R.id.tvSensor);
        s_Update = findViewById(R.id.switchUpdate);
        s_BattSaver = findViewById(R.id.switchBatterySaver);


        //set all properties of LocationRequest
        locationRequest = new LocationRequest();

        //How often the default location request occurs (30 seconds)
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        //How fast the location request occurs at max performance (5 seconds)
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        //Method by which the location is collected
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        s_BattSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s_BattSaver.isChecked()){
                    //most accurate - use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_Sensor.setText("Using GPS sensors");
                }
                else{
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_Sensor.setText("Using Towers + WiFi");
                }
            }
        });

        updateGPS();
    } // end onCreate method

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                }
                else {
                    Toast.makeText(this, "This app needs permssions granted to run propperly", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void updateGPS(){
        //get permissions from the user to track GPS
        //get the current location from  the fused client
        //update the UI to display the propper values on the screen

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permission. Put the values of location into the UI components
                    updateUIValues(location);
                }
            });
        }
        else {
            //permissions not granted yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }


    }

    private void updateUIValues(Location location) {
        //update all of the text view objects with a new location.
        tv_Lat.setText(String.valueOf(location.getLatitude()));
        tv_Long.setText(String.valueOf(location.getLongitude()));
        tv_Acc.setText(String.valueOf(location.getSpeed()));

        //Some phones don't have altitude, so we display it if we offer it
        if (location.hasAltitude()) {
            tv_Alt.setText(String.valueOf(location.getAltitude()));
        }
        else{
            tv_Alt.setText("Not Available");
        }

        //Same deal for Speed
        if(location.hasSpeed()){
            tv_Speed.setText(String.valueOf(location.getSpeed()));
        }
        else {
            tv_Speed.setText("Not Available");
        }



    }

}