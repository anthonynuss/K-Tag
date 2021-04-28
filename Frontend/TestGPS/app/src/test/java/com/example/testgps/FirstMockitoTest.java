//package com.example.testgps;
//import com.google.android.gms.maps.model.LatLng;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
////Test cases
////TODO Test 1: Give two different users
////TODO Test 2: Give two of the same user
////TODO Test 3: Give empty element
////TODO Test 4: Give two teams to test team sorting
//
//
//public class FirstMockitoTest {
//    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//  //  @Test
//  //  public void zero() throws JSONException{
//    //     JSONObject testObject = new JSONObject();
//       // testObject.put("Boolean", "true");
//       // System.out.println(testObject.getBoolean("Boolean"));
//   // }
//
//    //TEST 1:
//    @Test
//    public void Test1() throws JSONException {
//        String mapOutput = null;
//        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
//        VolleyMethods volleyHandler = mock(VolleyMethods.class);
//        //creating a maps activity to use the fake volley method stuff!
//        MapsActivity mapsActivity = new MapsActivity();
//
//        //creating two arbitrary user generated GPS locations that would come from the server
//        LatLng userLatLng = new LatLng(1, 1);
//        LatLng friendLatLng = new LatLng(2, 2);
//
//        //creating user JSON Object
//        JSONObject user = new JSONObject();
//        user.put("latitude", userLatLng.latitude);
//        user.put("longitude", userLatLng.longitude);
//        user.put("name", "Jimbob");
//
//        //creating friend JSON Object
//        JSONObject friend = new JSONObject();
//        friend.put("latitude", friendLatLng.latitude);
//        friend.put("longitude", friendLatLng.longitude);
//        friend.put("name", "Joe");
//
//
//        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);
//
//        //Mocking volley call to get JSONObject
//        JSONObject result = volleyHandler.getJsonObjReq();
//        System.out.println(result);
//        //Checking that user lat matches
//        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));
//
//        //accessing the second .thenReturn from line 56
//        result = volleyHandler.getJsonObjReq();
//        //Checking that user lat matches
//        Assert.assertEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));
//
//        //Creating text version of the map output
//        for(int i = 0; i < 2; i++){
//            if(user.getString("name") != friend.getString("name")){
//                mapOutput = "User location is " + userLatLng + " and Friend location is " + friendLatLng;
//            }
//            else{
//                mapOutput = "User location is " + userLatLng;
//            }
//        }
//        Assert.assertEquals(("User location is " + userLatLng + " and Friend location is " + friendLatLng), mapOutput);
//    }
//
//    //TEST 2:
//    @Test
//    public void Test2() throws JSONException {
//        String mapOutput = null;
//        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
//        VolleyMethods volleyHandler = mock(VolleyMethods.class);
//        //creating a maps activity to use the fake volley method stuff!
//        MapsActivity mapsActivity = new MapsActivity();
//
//        //creating two arbitrary user generated GPS locations that would come from the server
//        LatLng userLatLng = new LatLng(1, 1);
//        LatLng friendLatLng = new LatLng(1, 1);
//
//        //creating user JSON Object
//        JSONObject user = new JSONObject();
//        user.put("latitude", userLatLng.latitude);
//        user.put("longitude", userLatLng.longitude);
//        user.put("name", "Jimbob");
//
//        //creating friend JSON Object
//        JSONObject friend = new JSONObject();
//        friend.put("latitude", friendLatLng.latitude);
//        friend.put("longitude", friendLatLng.longitude);
//        friend.put("name", "Jimbob");
//
//
//        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);
//
//        //Mocking volley call to get JSONObject
//        JSONObject result = volleyHandler.getJsonObjReq();
//        System.out.println(result);
//        //Checking that user lat matches
//        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));
//
//        //accessing the second .thenReturn from line 56
//        result = volleyHandler.getJsonObjReq();
//        //Checking that user lat matches
//        Assert.assertEquals((Double)friendLatLng.latitude, (Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertEquals((Double)friendLatLng.longitude,  (Double)result.getDouble("longitude"));
//
//        //Creating text version of the map output
//        for(int i = 0; i < 2; i++){
//            if(user != friend){
//                mapOutput = "User location is " + userLatLng + " and Friend location is " + friendLatLng;
//            }
//            else{
//                mapOutput = "User location is " + userLatLng;
//            }
//        }
//        Assert.assertEquals(("User location is " + userLatLng + " and Friend location is " + friendLatLng), mapOutput);
//
//    }
//
//    //TEST 3:
//    /*@Test
//    public void Test3() throws JSONException {
//        String mapOutput = null;
//        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
//        VolleyMethods volleyHandler = mock(VolleyMethods.class);
//        //creating a maps activity to use the fake volley method stuff!
//        MapsActivity mapsActivity = new MapsActivity();
//
//        //creating two arbitrary user generated GPS locations that would come from the server
//        LatLng userLatLng = new LatLng(1, 1);
//        LatLng friendLatLng = new LatLng(1, 1);
//
//        //creating user JSON Object
//        JSONObject user = new JSONObject();
//        user.put("latitude", userLatLng.latitude);
//        user.put("longitude", userLatLng.longitude);
//        user.put("name", "Jimbob");
//
//        //creating empty friend JSON Object
//        JSONObject friend = new JSONObject();
//
//
//        when(volleyHandler.getJsonObjReq()).thenReturn(user).thenReturn(friend);
//
//        //Mocking volley call to get JSONObject
//        JSONObject result = volleyHandler.getJsonObjReq();
//        System.out.println(result);
//        //Checking that user lat matches
//        Assert.assertEquals((Double)userLatLng.latitude, (Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertEquals((Double)userLatLng.longitude,  (Double)result.getDouble("longitude"));
//
//        //accessing the second .thenReturn from line 56
//        result = volleyHandler.getJsonObjReq();
//        //Checking that user lat matches
//        Assert.assertNull((Double)result.getDouble("latitude"));
//        //Checking that user lng matches
//        Assert.assertNull((Double)result.getDouble("longitude"));
//
//        //Creating text version of the map output
//        for(int i = 0; i < 2; i++){
//            if(user != friend){
//                mapOutput = "User location is " + userLatLng + " friend is an invalid object";
//            }
//            else{
//                mapOutput = "User location is " + userLatLng;
//            }
//        }
//        Assert.assertEquals(("User location is " + userLatLng + " friend is an invalid object"), mapOutput);
//
//    }
//    */
//    //TEST 4:
//    //Handling two whole teams!
//    @Test
//    public void Test4() throws JSONException {
//        String mapOutput = null;
//        //Creating a mock object of the VolleyMethods class (because it isn't created yet!)
//        VolleyMethods volleyHandler = mock(VolleyMethods.class);
//        //creating a maps activity to use the fake volley method stuff!
//        MapsActivity mapsActivity = new MapsActivity();
//
//        //creating two arbitrary teams that would come from the server
//        JSONArray userTeam = new JSONArray();
//        JSONArray oppTeam = new JSONArray();
//        JSONObject player1 = new JSONObject().put("name", "Joe");
//        LatLng P1latLng = new LatLng(2, 3);
//        player1.put("location", P1latLng);
//        player1.put("latitude", P1latLng.latitude);
//        player1.put("longitude", P1latLng.longitude);
//
//        JSONObject player2 = new JSONObject().put("name", "Steve");
//        LatLng P2latLng = new LatLng(3, 3);
//        player2.put("location", P2latLng);
//        player2.put("latitude", P2latLng.latitude);
//        player2.put("longitude", P2latLng.longitude);
//
//        JSONObject player3 = new JSONObject().put("name", "Roger");
//        LatLng P3latLng = new LatLng(5, 4);
//        player3.put("location", P3latLng);
//        player3.put("latitude", P3latLng.latitude);
//        player3.put("longitude", P3latLng.longitude);
//
//        userTeam.put(player1);
//        userTeam.put(player2);
//        userTeam.put(player3);
//        mapsActivity.userTeamCreate(userTeam);
//        //creating two arbitrary teams that would come from the server
//        JSONArray opponentTeam = new JSONArray();
//
//        JSONObject player4 = new JSONObject().put("name", "Jack");
//        LatLng P4latLng = new LatLng(9, 7);
//        player4.put("location", P4latLng);
//        player4.put("latitude", P4latLng.latitude);
//        player4.put("longitude", P4latLng.longitude);
//
//        JSONObject player5 = new JSONObject().put("name", "Vicky");
//        LatLng P5latLng = new LatLng(15, 15);
//        player5.put("location", P5latLng);
//        player5.put("latitude", P5latLng.latitude);
//        player5.put("longitude", P5latLng.longitude);
//
//        JSONObject player6 = new JSONObject().put("name", "Carly");
//        LatLng P6latLng = new LatLng(3, 3);
//        player6.put("location", P6latLng);
//        player6.put("latitude", P6latLng.latitude);
//        player6.put("longitude", P6latLng.longitude);
//
//        opponentTeam.put(player4);
//        opponentTeam.put(player5);
//        opponentTeam.put(player6);
//        mapsActivity.oppTeamCreate(opponentTeam);
//
//        when(volleyHandler.getJsonArrReqInitial()).thenReturn(userTeam).thenReturn(opponentTeam);
//
//        //Mocking volley call to get JSONObject
//        JSONArray result = volleyHandler.getJsonArrReqInitial();
//        System.out.println(result);
//        //creating userTeam
//        mapsActivity.userTeamCreate(result);
//
//        //creating opponentTeam
//        result = volleyHandler.getJsonArrReqInitial();
//        System.out.println(result);
//        mapsActivity.oppTeamCreate(result);
//
//        //Accessing singleton to build expected string
//        UserTeamSingletonTest Team = new UserTeamSingletonTest().getInstance();
//        String actual = mapsActivity.teamPingTest();
//
//        String expected = "User Joe location is " + player1.get("location") + "\n"
//                + "User Steve location is " + player2.get("location") + "\n"
//                +"User Roger location is " + player3.get("location") + "\n"
//                +"User Jack location is " + player4.get("location") + "\n"
//                +"User Vicky location is " + player5.get("location") + "\n"
//                +"User Carly location is " + player6.get("location") + "\n";
//        Assert.assertEquals(expected, actual);
//
//    }
//}
