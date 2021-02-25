package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ShowSavedLocationsList extends AppCompatActivity {

    ListView lv_savedLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_locations_list);

        lv_savedLocations = findViewById(R.id.lv_wayPoints);

        CoordinatesList myApplication = (CoordinatesList)getApplicationContext();
        List<Double> savedLocationsLat = myApplication.getMyLocationsLat();
        List<Double> savedLocationsLng = myApplication.getMyLocationsLng();

        lv_savedLocations.setAdapter(new ArrayAdapter<Double>(this,android.R.layout.simple_list_item_1, savedLocationsLat));
        lv_savedLocations.setAdapter(new ArrayAdapter<Double>(this,android.R.layout.simple_list_item_1, savedLocationsLng));
    }
}