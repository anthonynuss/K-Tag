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
import com.android.volley.toolbox.JsonArrayRequest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author bendu
 *
 * MapsActivity displays map of all users in game. Places a pin with name of each user at their location
 *
 *
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    //maps variables
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
    JSONArray serverArray;
    double friendLat;
    double friendLng;
    String friendName;
    String friendId;
    double myLat;
    double myLng;


    /**
     * onCreate starts up the locationCallBack function and the locationRequest service using Fused location services provider
     * Makes volley calls to send and recieve user data and then places that user data on the map
     *
     * @param savedInstanceState
     */
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

        //using singleton to get username and password
        UserSingleton user = UserSingleton.getInstance();
        userName = user.getName();
        passWord = user.getPass();

        postJsonObjReq(); //uncomment to post Json obj req


        Log.v(TAG, "Logs work!");
        //Hopefully getting friend name first
        getJsonArrReqInitial();
       // Log.v(TAG, "friend name" + friendName);
        //getJsonObjReq();

        updateGPS();

    }

    /**
     * onResume starts up the location updates
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "Inside onResume");

        startLocationUpdates();

    }


    /**
     * updateGPS will be used to gain the most recently available GPS coordinates for the users phone and pushes them to the map
     */
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
                /**
                 * onSuccess calls the updateUIValues function and passes in the location parameter which is set by the fusedLocationProviderClient
                 *
                 * @param location
                 */
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

    /**
     * updateUIValues takes the location given by the updateGPS function and places all locations within it onto the map
     * @param location
     * @throws JSONException
     */
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
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(coords).title(userName));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).position(friend).title(friendName));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, zoomLevel));
        postJsonObjReq();
    }

    /**
     * startLocationUpdates is used to start up the fusedLocationProviderClient and then updates the GPS for the first time
     *
     *
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


    /**
     * onMapReady initializes the map
     *
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //HERE IS WHERE OUR CODE ACTUALLY STARTS!
    }


    /**
     * onRequestPermissionsResult checks for permissions before starting up location services. If permissions are not allowed it allerts the user that permissions must be granted. If permissions are allowed the GPS location is updated.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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
     * showProgressDialog is used to show the volley request progress graphically
     */

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * hideProgressDialog is used to stop the graphical progress representation
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * getJSONObjRec uses volley to request to get other user names and locations
     */
    private void getJsonObjReq() {
        Log.v(TAG, "friend name" + friendName);
        showProgressDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTIDPATH + friendId, null,
                new Response.Listener<JSONObject>() {

                    /**
                     * onResponse parses the user data once a sucessfull volley call has been completed.
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.v(TAG, response.toString());
                        //Log.v(TAG, "the friend is initialized!");
                        //friendObject = response;
                        try {
                           //Parsing the friend object to receive lat and lng coords
                           friendLat = friendObject.isNull("latitude") ? null : friendObject.getDouble("latitude");
                           friendLng = friendObject.isNull("longitude") ? null : friendObject.getDouble("longitude");
                           friendName = friendObject.isNull("name") ? null : friendObject.getString("name");
                            Log.v(TAG, "Testing to make sure the friend is still" + friendName + ": " + friendObject.get("name").toString());
                            Log.v(TAG, "friendLat: "+ friendLat);
                            Log.v(TAG, "friendLng: " + friendLng);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            /**
             * onErrorResponse handles any errors when a volley request does not complete sucessfully
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    /**
     * getJsonArrReqInitial is the function used to request all user data from the server initially so each user name can be used to make individual volley request
     */
    private void getJsonArrReqInitial() {
        showProgressDialog();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTServer, null,
                new Response.Listener<JSONArray>() {


                    /**
                     * onResponse loops through the recieved JSON array to find each user name
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the server is here");

                        serverArray = response;
                        for(int i = 0; i < serverArray.length(); i++){
                            try {
                                friendObject = serverArray.getJSONObject(i);
                                friendName = friendObject.get("name").toString();
                                friendId = friendObject.get("id").toString();
                                Log.v(TAG, "Friend name: " + friendName);
                                if(!friendName.equals(userName)){
                                    Log.v(TAG, "This is the name we use as friend!!!!: " + friendName);
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            /**
             * onErrorResponse responds to errors if there is an unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrReq, tag_json_obj);
    }

    /**
     * postJsonObjReq takes the user name,  password, and location and sends it via volley to the server
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECT + userName  , object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                      * @param response
                     */
                @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {
            /**\
             * onErrorResponse handles any unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
               // volleyRec.setText("Error getting response");
            }
        });
        hideProgressDialog();
        requestQueue.add(jsonObjectRequest);
    }
}
