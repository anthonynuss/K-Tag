package com.example.testgps;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

public class VolleyMethods {
    private static final String TAG = "VolleyMethods";
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
     * showProgressDialog is used to show the volley request progress graphically
     */

    public void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * hideProgressDialog is used to stop the graphical progress representation
     */
    public void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * getJSONObjRec uses volley to request to get other user names and locations
     */
    public void getJsonObjReq() {
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
                    Log.v(TAG, "We got it");
                        //Parsing the friend object to receive lat and lng coords
                        //friendLat = friendObject.isNull("latitude") ? null : friendObject.getDouble("latitude");
                        //friendLng = friendObject.isNull("longitude") ? null : friendObject.getDouble("longitude");
                        //friendName = friendObject.isNull("name") ? null : friendObject.getString("name");
                        //Log.v(TAG, "Testing to make sure the friend is still" + friendName + ": " + friendObject.get("name").toString());
                        //Log.v(TAG, "friendLat: "+ friendLat);
                        //Log.v(TAG, "friendLng: " + friendLng);

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
    public void getJsonArrReqInitial() {
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
    public void postJsonObjReq() {
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
     //   RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

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
       // requestQueue.add(jsonObjectRequest);
    }


}

