package com.example.testgps;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;
//This class is a singleton that allows us to have 2 universal lists of latitude and longitude values for each app user. Whenever an instance of this class is called
//it returns the main instance instead of creating a new one
public class CurrentLocation extends Application {

    //declaring the class as a singleton
    private static CurrentLocation singleton;
    //this class holds one location variable
    public static Location location;
    private Double Lat, Lng;
    public boolean updateFlag;
    //calling this.getMyLocationLat will return the latitude coordinates for the GPS
   public Double getMyLocationLat(){
       Lat = location.getLatitude();
       return Lat;
   }

    //calling this.getMyLocationLng will return the longitude coordinates for the GPS
    public Double getMyLocationLng(){
       Lng = location.getLongitude();
       updateFlag = true;
       return Lng;
   }

    //Calling this will provide the location singleton in any other class it is called in
    public CurrentLocation getInstance(){
        return singleton;
    }

    //The onCreate method for this class to bring our singleton to life!
    public void onCreate(){
        super.onCreate();
        singleton = this;
        Lat = 0.0;
        Lng = 0.0;
    }
}
