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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Test cases
//TODO Test 1: Give two different users
//TODO Test 2: Give two of the same user
//TODO Test 3: Give empty element
//TODO Test 4: Give two teams to test team sorting


public class FirstMockitoTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void zero() throws JSONException{
        JSONObject testObject = new JSONObject();
        testObject.put("Boolean", "true");
        System.out.println(testObject.getBoolean("Boolean"));
    }

    //TEST 1:
    @Test
    public void Test1() throws JSONException {
        String mapOutput = null;
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(1, 1);
        LatLng friendLatLng = new LatLng(2, 2);

        //creating user JSON Object
        JSONObject user = new JSONObject();
        user.put("latitude", userLatLng.latitude);
        user.put("longitude", userLatLng.longitude);
        user.put("name", "Jimbob")

        //creating friend JSON Object
        JSONObject friend = new JSONObject();
        friend.put("latitude", friendLatLng.latitude);
        friend.put("longitude", friendLatLng.longitude);
        friend.put("name", "Joe");


        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);

        //Mocking volley call to get JSONObject
        JSONObject result = volleyHandler.getJsonObjReq();
        System.out.println(result);
        //Checking that user lat matches
        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));

        //accessing the second .thenReturn from line 56
        result = volleyHandler.getJsonObjReq();
        //Checking that user lat matches
        Assert.assertEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));

        //Creating text version of the map output
        for(int i = 0; i < 2; i++){
            if(user.getString("name") != friend.getString("name")){
                mapOutput = "User location is " + userLatLng + " and Friend location is " + friendLatLng;
            }
            else{
                mapOutput = "User location is " + userLatLng;
            }
        }
        Assert.assertEquals(("User location is " + userLatLng + " and Friend location is " + friendLatLng), mapOutput);

    }

    //TEST 2:
    @Test
    public void Test2() throws JSONException {
        String mapOutput = null;
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(1, 1);
        LatLng friendLatLng = new LatLng(1, 1);

        //creating user JSON Object
        JSONObject user = new JSONObject();
        user.put("latitude", userLatLng.latitude);
        user.put("longitude", userLatLng.longitude);
        user.put("name", "Jimbob");

        //creating friend JSON Object
        JSONObject friend = new JSONObject();
        friend.put("latitude", friendLatLng.latitude);
        friend.put("longitude", friendLatLng.longitude);
        friend.put("name", "Jimbob");


        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);

        //Mocking volley call to get JSONObject
        JSONObject result = volleyHandler.getJsonObjReq();
        System.out.println(result);
        //Checking that user lat matches
        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));

        //accessing the second .thenReturn from line 56
        result = volleyHandler.getJsonObjReq();
        //Checking that user lat matches
        Assert.assertEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));

        //Creating text version of the map output
        for(int i = 0; i < 2; i++){
            if(user != friend){
                mapOutput = "User location is " + userLatLng + " and Friend location is " + friendLatLng;
            }
            else{
                mapOutput = "User location is " + userLatLng;
            }
        }
        Assert.assertEquals(("User location is " + userLatLng + " and Friend location is " + friendLatLng), mapOutput);

    }

    //TEST 3:
    @Test
    public void Test3() throws JSONException {
        String mapOutput = null;
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(1, 1);
        LatLng friendLatLng = new LatLng(1, 1);

        //creating user JSON Object
        JSONObject user = new JSONObject();
        user.put("latitude", userLatLng.latitude);
        user.put("longitude", userLatLng.longitude);
        user.put("name", "Jimbob");

        //creating empty friend JSON Object
        JSONObject friend = new JSONObject();


        when(volleyHandler.getJsonObjReq()).thenReturn(user);

        //Mocking volley call to get JSONObject
        JSONObject result = volleyHandler.getJsonObjReq();
        System.out.println(result);
        //Checking that user lat matches
        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));

        //accessing the second .thenReturn from line 56
        result = volleyHandler.getJsonObjReq();
        //Checking that user lat matches
        Assert.assertNotEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertNotEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));

        //Creating text version of the map output
        for(int i = 0; i < 2; i++){
            if(user != friend){
                mapOutput = "User location is " + userLatLng + " friend is an invalid object";
            }
            else{
                mapOutput = "User location is " + userLatLng;
            }
        }
        Assert.assertEquals(("User location is " + userLatLng + " friend is an invalid object"), mapOutput);

    }

    //TEST 4:
    //Handling two whole teams!
    @Test
    public void Test4() throws JSONException {
        String mapOutput = null;
        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
        VolleyMethods volleyHandler = mock(VolleyMethods.class);
        //creating a maps activity to use the fake volley method stuff!
        MapsActivity mapsActivity = new MapsActivity();

        //creating two arbitrary user generated GPS locations that would come from the server
        LatLng userLatLng = new LatLng(1, 1);
        LatLng friendLatLng = new LatLng(1, 1);

        //creating user JSON Object
        JSONObject user = new JSONObject();
        user.put("latitude", userLatLng.latitude);
        user.put("longitude", userLatLng.longitude);
        user.put("name", "Jimbob");

        //creating empty friend JSON Object
        JSONObject friend = new JSONObject();


        when(volleyHandler.getJsonObjReq()).thenReturn(user);

        //Mocking volley call to get JSONObject
        JSONObject result = volleyHandler.getJsonObjReq();
        System.out.println(result);
        //Checking that user lat matches
        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));

        //accessing the second .thenReturn from line 56
        result = volleyHandler.getJsonObjReq();
        //Checking that user lat matches
        Assert.assertNotEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
        //Checking that user lng matches
        Assert.assertNotEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));

        //Creating text version of the map output
        for(int i = 0; i < 2; i++){
            if(user != friend){
                mapOutput = "User location is " + userLatLng + " friend is an invalid object";
            }
            else{
                mapOutput = "User location is " + userLatLng;
            }
        }
        Assert.assertEquals(("User location is " + userLatLng + " friend is an invalid object"), mapOutput);

    }
}
