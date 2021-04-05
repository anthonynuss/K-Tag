package com.example.testgps;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
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
    public void zero() throws JSONException{
        JSONObject testObject = new JSONObject();
        testObject.put("Boolean", true);
        System.out.println(testObject.getBoolean("Boolean"));
    }

    @Test
    public void testMethod() throws JSONException {
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(1, 1);
        LatLng friendLatLng = new LatLng(1, 1);
        JSONObject user = new JSONObject().put("latitude", userLatLng.latitude).put("longitude", userLatLng.longitude);

        System.out.println(user.toString());
        System.out.println(user);
        JSONObject friend = new JSONObject();
        try {
            friend.put("latitude", friendLatLng.latitude);
            friend.put("longitude", friendLatLng.longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
      //  JSONObject user = new JSONObject();

        //JSONObject friend = new JSONObject();



        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);

        JSONObject result = volleyHandler.getJsonObjReq();
        System.out.println(result);
        //Checking that user lat matches
        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));

        //when(volleyHandler.getJsonObjReq()).thenReturn(friend);

        result = volleyHandler.getJsonObjReq();
        //Checking that user lat matches
        Assert.assertEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));
    }
}
