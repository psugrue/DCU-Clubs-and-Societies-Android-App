package com.example.college.dcuclubsandsocs;

/**
 * Created by College on 11/03/2015.
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
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

public class CreateThread extends Activity implements OnClickListener{

    private EditText title,message;
    private Button mCreate;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String REGISTER_URL = "http://52.16.161.96/createThread.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_thread);

        title   = (EditText)findViewById(R.id.create_title);
        message = (EditText)findViewById(R.id.create_message);

        mCreate = (Button)findViewById(R.id.createT);
        mCreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new cThread().execute();
    }

    class cThread extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(CreateThread.this);
            progressDialog.setMessage("Creating thread....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args){
            int success;
            String title1   = title.getText().toString();
            String message1 = message.getText().toString();

            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("title",title1));
                params.add(new BasicNameValuePair("message", message1));

                Log.d("request!","starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params
                );

                if(json!=null){
                    Log.d("Attempting to create.", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if(success == 1){
                        Log.d("Thread Created!", json.toString());
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
            Intent intent = new Intent(getApplicationContext(),SocHomePageActivity.class);
            startActivity(intent);
        }
    }
}
