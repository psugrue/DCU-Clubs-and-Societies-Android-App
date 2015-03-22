package com.example.college.dcuclubsandsocs;

/**
 * Created by College on 11/03/2015.
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class CreateEvent extends Activity implements OnClickListener{

    private EditText society,title,message,numAtt;
    private Button mCreate;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String CREATE_EVENT_URL = "http://52.16.161.96/createEvent.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        title   = (EditText)findViewById(R.id.create_event_title);
        message = (EditText)findViewById(R.id.create_event_message);
        numAtt = (EditText)findViewById(R.id.numberAttending);
        society = (EditText)findViewById(R.id.create_event_title2);



        Log.d("Testing textview", "text");

        mCreate = (Button)findViewById(R.id.createE);
        mCreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        new cEvent().execute();
    }

    class cEvent extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(CreateEvent.this);
            progressDialog.setMessage("Creating event....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args){
            int success;
            String title1   = title.getText().toString();
            String message1 = message.getText().toString();
            String number = numAtt.getText().toString();
            String soc=society.getText().toString();



            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("society",soc));
                params.add(new BasicNameValuePair("title",title1));
                params.add(new BasicNameValuePair("message",message1));
                params.add(new BasicNameValuePair("number", number));


                Log.d("request!","starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        CREATE_EVENT_URL, "POST", params
                );

                if(json!=null){
                    Log.d("Attempting to create.", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if(success == 1){
                        Log.d("Event Created!", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    }
                    else{
                        Log.d("Event Creation Failed", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }

                }

            }
            catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url){
            progressDialog.dismiss();
            Intent intent = new Intent(getApplicationContext(),EventPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
