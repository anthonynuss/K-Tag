package com.example.testgps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity<LocationCallBack> extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 42;
    //Text descriptions of data from GPS in the UI
    TextView tv_Lat, tv_Long, tv_Update, tv_Sensor, volleyRec, user_name;
    //Switches to remember if we are tracking location or not,
    //and to switch between high performance and battery saver
    Switch s_Update, s_HighPerformance;

    Button b_ShowMap, b_VolleyTest, b_enterInfo;

    String userName = null;
    String passWord = null;
    long millis;
    java.sql.Date date;

    //variables to set the default and fast update intervals
    public static final int DEFAULT_UPDATE_INTERVAL = 1;
    public static final int FAST_UPDATE_INTERVAL = 1;
    private static final String TAG = "MainActivity";
    boolean updateOn = false;

    //stuff for volley req//MOVED
    private String tag_json_obj = "jobj_req";
    private ProgressDialog pDialog;

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

        tv_Lat = findViewById(R.id.tvLat);
        tv_Long = findViewById(R.id.tvLong);
        tv_Update = findViewById(R.id.tvUpdate);
        tv_Sensor = findViewById(R.id.tvSensor);
        volleyRec = findViewById(R.id.volleyRec);
        user_name = findViewById(R.id.userName);

        s_Update = findViewById(R.id.switchUpdate);
        s_HighPerformance = findViewById(R.id.switchBatterySaver);
        b_ShowMap = findViewById(R.id.buttonShowMap);
        b_VolleyTest = findViewById(R.id.VolleyTest);
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

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        user_name.setText("Enter Info!");
        //gets user info from InfoActivity
        Intent i = getIntent();
        if(getIntent().getExtras() != null)

        {
            userName = i.getStringExtra("Uname");
            passWord = i.getStringExtra("Pword");
            Log.v(TAG, "password: " + passWord);
            Intent j = new Intent(MainActivity.this, MapsActivity.class);




            user_name.setText(userName); //Updates user name
            millis =System.currentTimeMillis();
            date = new java.sql.Date(millis);
           // postJsonObjReq(); //uncomment to post Json obj req
        }



        //This button starts the map activity
        b_ShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("Uname", userName);
                i.putExtra("Pword", passWord);
                startActivity(i);
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







        s_HighPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_HighPerformance.isChecked()) {
                    //most accurate - use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_Sensor.setText("Using GPS sensors");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_Sensor.setText("Using Towers + WiFi");
                }
            }
        });

        s_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_Update.isChecked()) {
                    //turn on location tracking
                    startLocationUpdates();
                } else {
                    //turn off tracking
                    stopLocationUpdates();
                }
            }
        }


        );

        //volley stuff


        b_VolleyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJsonObjReq();
            }
        });

        b_enterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });

        updateGPS();
    } // end onCreate method






    private void startLocationUpdates() {
        tv_Update.setText("Location is being tracked");
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


    private void stopLocationUpdates(){
        tv_Update.setText("Location Disabled");
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

    private void updateUIValues(Location location) {
        //update all of the text view objects with a new location.
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        Log.v(TAG, "Lat:" + coords.latitude +  "Lng: " + coords.longitude);
        tv_Lat.setText(String.valueOf(location.getLatitude()));
        tv_Long.setText(String.valueOf(location.getLongitude()));
    }


    /**
     * for volley
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    /**
     * getting json object request
     * */
    private void getJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        volleyRec.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    /**
     * putting a json object
     */
    private void postJsonObjReq() {
        showProgressDialog();
        JSONObject object = new JSONObject();
        try {
            object.put("name", userName);
            object.put("password", passWord);
            object.put("longitude", "0.0001");
            object.put("latitude", "1.221");
            //object.put("joiningDate", "datetoString");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECT, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyRec.setText("Error getting response");
            }
        });
        hideProgressDialog();
        requestQueue.add(jsonObjectRequest);
    }

};



