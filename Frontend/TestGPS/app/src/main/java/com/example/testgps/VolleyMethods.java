package com.example.testgps;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class VolleyMethods {

    /*ToDO: This class right now has an implementation  where we pass in the request path for the server
    Need to test if this works on other classes
     */
    private static final String TAG = "VolleyMethods";
    private String tag_json_obj = "jobj_req";
    String[] kvPairs = {"name", "password", "latitude", "longitude"};

    public Object getJsonObject;

    //public void getJsonObjReq(){return;}

    //This is the getter for our JSON objects
    //It creates a new Response listener and pulls a single object from the server
    //Then it handles the object by taking the data from the object and putting it into the bogey singleton
    //From there we can use the bogey singleton to do stuff!
    public void getJsonObjReq(String requestPath){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestPath, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                //TODO: fill different fields from our server to the bogey singleton
                //Accessing the bogey singleton after receiving the JSONObject and filling it with meaningful data
                BogeySingleton bogey = BogeySingleton.getInstance();
                try {
                    bogey.setName(response.get("name").toString());
                    bogey.setPass(response.get("password").toString());
                    //FIXME: Finish setting singleton
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
                }, new Response.ErrorListener() {
            /**
             * onErrorResponse handles any errors when a volley request does not complete sucessfully
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error: " + error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
    }

    //This method is the getter for JSONArrays. Works the same as the JSONObject getter, but just bigger
     public void getJsonArrReqInitial(Boolean Team, String requestPath){
         JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                 requestPath, null,
                 new Response.Listener<JSONArray>() {
                     /**
                      * onResponse loops through the recieved JSON array to find each user name
                      * @param response
                      */
                     @Override
                     public void onResponse(JSONArray response) {
                                 UserTeamSingleton userTeam = UserTeamSingleton.getInstance();
                                 //setting either the User's team, or the opponents team
                              if(Team) {
                                  userTeam.setTeam(response);
                              }
                              else{
                                  userTeam.setOpp(response);
                                 }
                         }
                 }, new Response.ErrorListener() {
             /**
              * onErrorResponse responds to errors if there is an unsuccessful volley request
              * @param error
              */
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.v(TAG, "Error: " + error.getMessage());
             }
         });
         AppController.getInstance().addToRequestQueue(jsonArrReq, tag_json_obj);
     }



     //try using variable arguments!!!!!!
    //for now we have to put in the variables in a specific order
     public void postJsonObjReq(String requestPath, String ...values) throws JSONException {
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

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, requestPath, object,
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


