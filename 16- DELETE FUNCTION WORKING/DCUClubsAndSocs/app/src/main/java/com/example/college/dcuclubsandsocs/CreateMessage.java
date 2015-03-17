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
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.college.dcuclubsandsocs.Login.appuser;
import static com.example.college.dcuclubsandsocs.SocHomePageFragment.society;

public class CreateMessage extends Activity implements OnClickListener{

    private EditText receiver,message;
    TextView text,text2;
    private Button mSend;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String SEND_MESSAGE_URL = "http://52.16.161.96/sendMessage.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String soc;

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_message);

        receiver = (EditText)findViewById(R.id.create_message_username);
        message = (EditText)findViewById(R.id.create_message2);


        Log.d("Testing textview", "text");

        mSend = (Button)findViewById(R.id.sendM);
        mSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        new cMessage().execute();
    }

    class cMessage extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(CreateMessage.this);
            progressDialog.setMessage("Sending Message....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args){
            int success;
            String receiver1 = receiver.getText().toString();
            String message1 = message.getText().toString();
            String username = appuser;
            String soc=society;


            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("receiver",receiver1));
                params.add(new BasicNameValuePair("message",message1));
                params.add(new BasicNameValuePair("username", appuser));


                Log.d("Receiver",receiver1);
                Log.d("Message",message1);
                Log.d("Username",appuser);


                Log.d("request!","starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        SEND_MESSAGE_URL, "POST", params
                );

                if(json!=null){
                    Log.d("Attempting to create.", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if(success == 1){
                        Log.d("Message Sent", json.toString());
                        finish();
                        return json.getString(TAG_MESSAGE);
                    }
                    else{
                        Log.d("Thread Creation Failed", json.getString(TAG_MESSAGE));
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
            Intent intent = new Intent(CreateMessage.this,HomePageActivity.class);
            startActivity(intent);
        }
    }
}
