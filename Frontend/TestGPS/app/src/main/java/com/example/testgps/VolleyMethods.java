package com.example.testgps;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class VolleyMethods {

    private static final String TAG = "VolleyMethods";

    String[] kvPairs = {"name", "password", "latitude", "longitude"};

    public Object getJsonObject;

    //public void getJsonObjReq(){return;}

    public JSONObject getJsonObjReq(){

        return null;}

     public JSONArray getJsonArrReqInitial(){return null;}



     //try using variable arguments!!!!!!
    //for now we have to put in the variables in a specific order
     public void postJsonObjReq(String ...values) throws JSONException {
        JSONObject object = new JSONObject();
        //looping through variable array and assigning key value pairs to object
        for(int i = 0; i < values.length; i++)
        {
            try{
                object.put(kvPairs[i], values[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //FIXME right now this line is broken because of getApplicationContext()
            //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECT + object.get("name"), object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            //FIXME this line is also broken because of the issue
            // with getApplicationContext for the request queue
            //requestQueue(jsonObjectRequest);

        }

     }




}


