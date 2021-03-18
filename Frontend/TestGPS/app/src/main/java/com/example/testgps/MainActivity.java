package com.example.testgps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity<LocationCallBack> extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 42;

    TextView tv_Lat, tv_Long, my_location, user_name; //textViews for screen

    Button b_ShowMap, b_VolleyTest, b_enterInfo; //buttons for screen

    String userName = ""; //Hasn't entered info yet
    String passWord = "";

    //variables to set the default and fast update intervals
    public static final int DEFAULT_UPDATE_INTERVAL = 1;
    public static final int FAST_UPDATE_INTERVAL = 1;
    private static final String TAG = "MainActivity";
    boolean updateOn = false;

    //current location
    Location currentLocation;

    //Location request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallBack;

    //Google's API for location services the app lives off this G
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textviews
        tv_Lat = findViewById(R.id.tvLat);
        tv_Long = findViewById(R.id.tvLong);
        my_location = findViewById(R.id.myLocation);
        user_name = findViewById(R.id.userName);

        //buttons
        b_ShowMap = findViewById(R.id.buttonShowMap);
        b_VolleyTest = findViewById(R.id.VolleyTest); //not used right now
        b_enterInfo = findViewById(R.id.buttonEnterInfo);

        //set all properties of LocationRequest
        locationRequest = new LocationRequest();

        //How often the default location request occurs (3 seconds)
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        //How fast the location request occurs at max performance (1 seconds)
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);

        //Method by which the location is collected
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //This event is triggered whenever the update interval is met
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //sending the new location value to the lat and lng UI
                updateUIValues(locationResult.getLastLocation());
            }
        };

        //gets user info from InfoActivity
        Intent i = getIntent();
        if(getIntent().getExtras() != null)
        {
            userName = i.getStringExtra("Uname");
            passWord = i.getStringExtra("Pword");
            Log.v(TAG, "password: " + passWord);
            Intent j = new Intent(MainActivity.this, MapsActivity.class);




            user_name.setText(userName); //Updates user name
        }

        //This button starts the map activity
        b_ShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName == ""){
                    user_name.setText("PLEASE ENTER INFO");
                }else {
                    Intent i = new Intent(MainActivity.this, MapsActivity.class);
                    i.putExtra("Uname", userName);
                    i.putExtra("Pword", passWord);
                    startActivity(i);
                }
            }
        });


        //Starting the login page
        b_enterInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });

        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //button that used to be "Location Updates
        //startLocationUpdates();

        updateGPS();
    } // end onCreate method


    /**
     * Call this method to start location updates
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);

        updateGPS();
    }

    /**
     * Call this method to stop location updates
     */
    private void stopLocationUpdates(){
        tv_Lat.setText("Location Disabled");
        tv_Long.setText("Location Disabled");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

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

    /**
     * Updates gps
     */
    private void updateGPS(){
        //get permissions from the user to track GPS
        //get the current location from  the fused client
        //update the UI to display the proper values on the screen

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user provided the permission
           // fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                fusedLocationProviderClient.getCurrentLocation(100, null).addOnSuccessListener(this, new OnSuccessListener<Location>() {
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

    /**
     * Updates my location text views
     * @param location my location
     */
    private void updateUIValues(Location location) {
        //update all of the text view objects with a new location.
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        Log.v(TAG, "Lat:" + coords.latitude +  "Lng: " + coords.longitude);
        tv_Lat.setText("Latitude: "+String.valueOf(location.getLatitude()));
        tv_Long.setText("Longitude: "+String.valueOf(location.getLongitude()));
    }

};



