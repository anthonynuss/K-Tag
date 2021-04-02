package com.example.testgps;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FirstMockitoTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMethod() throws JSONException {
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(0, 0);
        LatLng friendLatLng = new LatLng(1, 1);

        JSONObject user = new JSONObject();
        JSONObject friend = new JSONObject();

        //creating the mocked JSONObjects for user and friend
        user.put("latitude", userLatLng.latitude);
        user.put("longitude", userLatLng.longitude);
        friend.put("latitude", friendLatLng.latitude);
        friend.put("longitude", friendLatLng.longitude);

        when(volleyHandler.getJsonObjReq()).thenReturn(user);
        when(volleyHandler.getJsonObjReq()).thenReturn(friend);

        //Checking that user lat matches
        Assert.assertEquals(java.util.Optional.of(userLatLng.latitude),  user.isNull("latitude") ? null : user.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals(java.util.Optional.of(userLatLng.longitude),  user.isNull("longitude") ? null : user.getDouble("longitude"));

        //Checking that friend lat matches
        Assert.assertEquals(java.util.Optional.of(friendLatLng.latitude),  friend.isNull("latitude") ? null : friend.getDouble("latitude"));
        //Checking that friend lng matches
        Assert.assertEquals(java.util.Optional.of(friendLatLng.longitude),  friend.isNull("longitude") ? null : friend.getDouble("longitude"));
    }
}
