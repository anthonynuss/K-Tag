package com.example.testgps;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   //private List<Double> savedLocationsLng;
    //private List<Double> savedLocationsLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //CoordinatesList coords = (CoordinatesList) getApplicationContext();
        //savedLocationsLat = coords.getMyLocationsLat();
        //savedLocationsLng = coords.getMyLocationsLng();
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
        int i = 0;
        float zoomLevel = 20.0f;
      //  savedLocations = myApplication.getMyLocations();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-53, 151);
       // LatLng userDot = new LatLng(v: )
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //HERE IS WHERE OUR CODE ACTUALLY STARTS!
        CurrentLocation myLocation = (CurrentLocation) getApplicationContext();
        LatLng location = new LatLng(myLocation.getMyLocationLat(), myLocation.getMyLocationLng());

        //trying to find a way to update location as the lat lang elements are updated
        if(myLocation.updateFlag == true){
             location = new LatLng(myLocation.getMyLocationLat(), myLocation.getMyLocationLng());
        }

                mMap.addMarker(new MarkerOptions().position(location).title("Here I am!"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
              //  myLocation.updateFlag = false;


        }
    }
