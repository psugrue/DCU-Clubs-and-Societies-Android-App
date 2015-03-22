package com.example.college.dcuclubsandsocs;

/**
 * Created by College on 10/03/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import static com.example.college.dcuclubsandsocs.ResetPassword.mEmail;

public class EnterCode extends Activity implements OnClickListener{

    private EditText code;
    private Button  mEnter;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //location of PHP file
    private static final String ENTER_CODE_URL = "http://52.16.161.96/validatePassword.php";


    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code);

        code = (EditText)findViewById(R.id.code);

        mEnter = (Button)findViewById(R.id.enterCode);
        mEnter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        new checkCode().execute();

    }

    class checkCode extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EnterCode.this);
            pDialog.setMessage("Entering code...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String newCode    = code.getText().toString();
            Log.d("Code", newCode);
            Log.d("Email", mEmail);


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                
                params.add(new BasicNameValuePair("email",mEmail));
				params.add(new BasicNameValuePair("code",newCode));



                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        ENTER_CODE_URL, "POST", params);

                Log.d("Registering attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    Intent i = new Intent(EnterCode.this, ChooseNewPassword.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
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
            if (file_url != null){
                Toast.makeText(EnterCode.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}