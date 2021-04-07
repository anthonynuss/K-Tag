package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Registers the user, creates them in the database
 */
public class CreateAccActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccActivity";
    Button b_back, b_Register;
    EditText u_name, ip_password1, ip_password2;
    TextView t_verify;
    String userName, password, passwordVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
        UserSingleton user = UserSingleton.getInstance();

        b_back = findViewById(R.id.buttonBack);
        b_Register = findViewById(R.id.buttonRegister);
        u_name = findViewById(R.id.userNameEnter);
        ip_password1 = findViewById(R.id.editTextPassword);
        ip_password2 = findViewById(R.id.editTextPassword2);
        t_verify = findViewById(R.id.textPassVerified);

        /**
         * onclick registers user if passwords lineup
         */
        b_Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userName = u_name.getText().toString();
                password = ip_password1.getText().toString();
                passwordVerify = ip_password2.getText().toString();

                Intent i = new Intent(CreateAccActivity.this, InfoActivity.class);
                //verify passwords match
                if(password.equals(passwordVerify)) {
                    user.setName(userName);
                    user.setPass(password);
                    postJsonObjReq();
                    startActivity(i);
                }else {
                    t_verify.setText("Passwords do not match!");
                }

            }
        });


        /**
         * onclick goes back to InfoActivity
         */
        b_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * postJsonObjReq takes the user name,  password, and sends it via volley to the server
     */
    private void postJsonObjReq() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", userName);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.URL_JSON_OBJECT, object,
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
        requestQueue.add(jsonObjectRequest);
    }
}