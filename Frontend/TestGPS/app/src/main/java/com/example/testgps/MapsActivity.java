package com.example.testgps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;





//THIS VERSION OF OUR CODE HAS FUSEDLOCATIONSERVCIES DIRECLTY IN THE MAP ACTIVITY
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "entered on create");
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
                updateUIValues(locationResult.getLastLocation());
            }
        };




        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Log.v(TAG, "Logs work!");
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
                    updateUIValues(location);
                }
            });

        } else {
            //permissions not granted yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 42);
            }
        }
    }

    private void updateUIValues(Location location){
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        Log.v(TAG, "Lat:" + coords.latitude +  "Lng: " + coords.longitude);
        mMap.addMarker(new MarkerOptions().position(coords).title("Here I am!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, zoomLevel));



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
}
