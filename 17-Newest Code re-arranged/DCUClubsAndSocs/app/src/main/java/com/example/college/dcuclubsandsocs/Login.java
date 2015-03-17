package com.example.college.dcuclubsandsocs;

/**
 * Created by College on 10/03/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity implements OnClickListener {

    private EditText user, pass;
    private Button mSubmit, mRegister,mReset;
    public static String appuser;

    // Progress Dialog
    private ProgressDialog pDialog;


    JSONParser jsonParser = new JSONParser();


    private static final String LOGIN_URL = "http://52.16.161.96/login.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // setup input fields
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        // setup buttons
        mSubmit = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        mReset = (Button)findViewById(R.id.resetPassword);

        // register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mReset.setOnClickListener(this);



    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(isOnline()){
            switch (v.getId()) {
                case R.id.login:
                    new AttemptLogin().execute();
                    break;
                case R.id.register:
                    Intent i = new Intent(this, Register.class);
                    startActivity(i);
                    break;
                case R.id.resetPassword:
                    Intent intent = new Intent(this, ResetPassword.class);
                    startActivity(intent);
                    break;

                default:
                    break;
            }
        }
        else{
            Toast.makeText(Login.this, "Ensure your device is connected to the internet.", Toast.LENGTH_LONG).show();

        }

    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            appuser=username;



            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
                        params);



                    // check your log for json response
                    Log.d("Login attempt", json.toString());

                    // json success tag
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Login Successful!", json.toString());
                        // save user data
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(Login.this);
                        Editor edit = sp.edit();
                        edit.putString("username", username);
                        edit.commit();

                        Intent i = new Intent(Login.this, HomePageActivity.class);
                        finish();
                        startActivity(i);
                        return json.getString(TAG_MESSAGE);
                    } else {
                        Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}