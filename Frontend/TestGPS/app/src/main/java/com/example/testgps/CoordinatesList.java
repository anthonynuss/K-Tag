package com.example.testgps;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;
//This class is a singleton that allows us to have 2 universal lists of latitude and longitude values for each app user. Whenever an instance of this class is called
//it returns the main instance instead of creating a new one
public class CoordinatesList extends Application {

    //declaring the class as a singleton
    private static CoordinatesList singleton;
    //this class holds two Double Lists for lat and lng
    private List<Double> myLocationsLat, myLocationsLng;

    //calling this.getMyLocationsLat will return the latitude array list
    public List<Double> getMyLocationsLat() {
        return myLocationsLat;
    }

    //calling this.getMyLocationsLng will return the longitude array list
    public List<Double> getMyLocationsLng(){
        return myLocationsLng;
    }

    //public void setMyLocations(List<Double> myLocations) {
      //  this.myLocationsLat = myLocationsLat;
        //this.myLocationsLng = myLocationsLng;
    //}

    public CoordinatesList getInstance(){
        return singleton;
    }

    //The onCreate method for this class to bring our singleton to life!
    public void onCreate(){
        super.onCreate();
        singleton = this;
        myLocationsLat = new ArrayList<>();
        myLocationsLng = new ArrayList<>();

    }
}
