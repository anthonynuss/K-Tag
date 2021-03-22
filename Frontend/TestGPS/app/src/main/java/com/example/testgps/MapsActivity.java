package com.example.testgps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Displays a map of all users in the games. Places a pin with name on each users location
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private GoogleMap mMap;
    float zoomLevel = 20.0f;
    //declaration for fusedLocationProviderClient
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public static final int DEFAULT_INTERVAL = 1;
    public static final int FAST_INTERVAL = 1;


    //stuff for volley req
    private String tag_json_obj = "jobj_req";
    private ProgressDialog pDialog;
    private String userName;
    private String passWord;
    JSONObject friendObject;
    double friendLat;
    double friendLng;
    double myLat;
    double myLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "entered on create");
        Log.v(TAG, "This is our userName" +  userName);
        //seting properites of LocationRequest
        locationRequest = new LocationRequest();
        //How often defualt location request occurs
        locationRequest.setInterval(1000 * DEFAULT_INTERVAL);
        //How fast the location request occurs at max performance
        locationRequest.setFastestInterval(1000 * FAST_INTERVAL);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //This event is triggered whenever the update interval is met

        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //sending the new location value to the lat and lng UI
                Log.v(TAG, "We are inside the callback function");
                try {
                    updateUIValues(locationResult.getLastLocation());
                } catch (JSONException e) {
                    Log.v(TAG, "stack Trace printed");
                    e.printStackTrace();
                }
            }
        };




        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        Intent k = getIntent();
        if(getIntent().getExtras() != null)

        {
            Log.v(TAG, "We see the password" + passWord);
            userName = k.getStringExtra("Uname");
            passWord = k.getStringExtra("Pword");
            Log.v(TAG, "Uname = " + userName);
            //postJsonObjReq(); //uncomment to post Json obj req
        }

        Log.v(TAG, "Logs work!");
        getJsonObjReq();
        updateGPS();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "Inside onResume");

        startLocationUpdates();

    }



    private void updateGPS() {
        //get permissions from the user to track GPS
        //get the current location from  the fused client
        //update the UI to display the proper values on the screen

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user provided the permission
           // fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
             //   @Override
               // public void onSuccess(Location location) {
                    // we got permission. Put the values of location into the UI components
                 //   CurrentLocation myLocation = (CurrentLocation) getApplicationContext();
                   // myLocation.location = location;
                    //updateUIValues(location);
                //}
            //});
            fusedLocationProviderClient.getCurrentLocation(100, null).addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    try {
                        updateUIValues(location);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            //permissions not granted yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 42);
            }
        }
    }

    private void updateUIValues(Location location) throws JSONException {

        getJsonObjReq();
        myLat = location.getLatitude();
        myLng = location.getLongitude();

        //Clearing old marker, so that only one marker is on the map at a time
        mMap.clear();
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng friend = new LatLng(friendLat, friendLng);
        Log.v(TAG, "Lat:" + coords.latitude +  "Lng: " + coords.longitude);
        Log.v(TAG, "Friend lat: " + friendLat + "Lng: " + friendLng);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(coords).title("Here I am!"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).position(friend).title("This is my friend!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, zoomLevel));
        postJsonObjReq();
       


    }

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

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper());

        updateGPS();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //HERE IS WHERE OUR CODE ACTUALLY STARTS!



        //mMap.addMarker(new MarkerOptions().position(location).title("Here I am!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

        //LatLng coords1 = new LatLng(myLocation.getMyLocationLat(), myLocation.getMyLocationLng() - 5);
        //mMap.addMarker(new MarkerOptions().position(coords1).title("Here I am!"));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 42:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app needs permssions granted to run propperly", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    /**
     * Volley Stuff
     */

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void getJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the friend is initialized!");
                        friendObject = response;
                        try {
                           //Parsing the friend object to receive lat and lng coords
                           friendLat = friendObject.isNull("latitude") ? null : friendObject.getDouble("latitude");
                           friendLng = friendObject.isNull("longitude") ? null : friendObject.getDouble("longitude");
                            Log.v(TAG, "friendLat: "+ friendLat);
                            Log.v(TAG, "friendLng: " + friendLng);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //try {
                          //   friendLat = Double.parseDouble(friendObject.getJSONObject("latitude").toString());
                           //  friendLng = Double.parseDouble(friendObject.getJSONObject("longitude").toString());
                        //} catch (JSONException e) {
                         //   e.printStackTrace();
                      //  }
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
            object.put("latitude", myLat);
            object.put("longitude", myLng);
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
               // volleyRec.setText("Error getting response");
            }
        });
        hideProgressDialog();
        requestQueue.add(jsonObjectRequest);
    }
}
