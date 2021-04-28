package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Anthony Nuss
 * Login page for user, asks for user info to login
 */

public class InfoActivity extends AppCompatActivity {

    //variables to hold and print username and password
    TextView ip_userName, ip_password, found_text;
    String userName, password;
    //login button to end intent
    Button b_Login, b_signUp;

    //log.v tag for debugging
    private static final String TAG = "InfoActivity";
    private static boolean userFound;
    private ProgressDialog pDialog;


    /**
     * onCreate, set textviews for users to enter info and button to login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //initializing EditTexts and login button
        ip_userName = findViewById(R.id.ip_userName);
        ip_password = findViewById(R.id.ip_password);
        found_text = findViewById(R.id.userFoundText);
        b_Login = findViewById(R.id.b_Login);
        b_signUp = findViewById(R.id.buttonSignUp);
        UserSingleton user = UserSingleton.getInstance();
        user.setName(null);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        b_Login.setOnClickListener(new View.OnClickListener() {

            /**
             * onClick finds user info in database. Sends user to main activity
             * @param v
             */
            @Override
            public void onClick(View v) {
                //getting username and password from the login page
                userName = ip_userName.getText().toString();
                password = ip_password.getText().toString();

                findLoginCredentials(); //find user. Login if found

            }
        });

        b_signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoActivity.this, CreateAccActivity.class);
                startActivity(i);
            }
        });

    }

    /**
     * showProgressDialog is used to show the volley request progress graphically
     */

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * hideProgressDialog is used to stop the graphical progress representation
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * findLoginCredentials gets all users from server and finds the user that the person entered by comparing username and password.
     */
    private void findLoginCredentials() {
        showProgressDialog();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTServer, null,
                new Response.Listener<JSONArray>() {

                    /**
                     * onResponse loops through the received JSON array to find each user name
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject userObject;
                        JSONArray serverArray;
                        String findUser;
                        String userPass;


                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the server is here");

                        serverArray = response;
                        for(int i = 0; i < serverArray.length(); i++){
                            try {
                                userObject = serverArray.getJSONObject(i);
                                findUser = userObject.get("name").toString();
                                userPass = userObject.get("password").toString();
                                Log.v(TAG, "User: " + findUser);
                                //if we find a person with same name and password it is a valid user.
                                if(findUser.equals(userName) && userPass.equals(password)){
                                    InfoActivity.userFound = true;
                                    UserSingleton user = UserSingleton.getInstance();
                                    user.setName(userName);
                                    user.setPass(password);
                                    user.setID(userObject.get("id").toString());

                                    Log.v(TAG, "We found the user: " + findUser);



                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();
                        }
                        //if user was found login. Else display not found message
                        UserSingleton user = UserSingleton.getInstance();
                        if(user.getName() != null) {
                            //findUserTeam();
                            //UserSingleton user = UserSingleton.getInstance();
                            Log.v(TAG, "UserName using singleton " + user.getName());
                            Log.v(TAG, "Password using singleton " + user.getPass());
                            Log.v(TAG, "Id using singleton " + user.getID());
                            Intent k = new Intent(InfoActivity.this, MainActivity.class);
                            startActivity(k);
                        }else {
                            found_text.setText("User not found");
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
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrReq, "jobj_req");
    }


    /**
     * Closes keyboard when user presses outside an input
     * @param view
     */
    public void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}