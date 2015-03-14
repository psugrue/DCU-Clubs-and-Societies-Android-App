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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.college.dcuclubsandsocs.Login.appuser;
import static com.example.college.dcuclubsandsocs.SocHomePageActivity.society;
import static com.example.college.dcuclubsandsocs.ThreadPageActivity.title;

public class CreateComment extends Activity implements OnClickListener{

    private EditText comment;
    TextView text;
    private Button mCreate;

    private ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String CREATE_COMMENT_URL = "http://52.16.161.96/createComment.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String soc;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_comment);


        comment = (EditText)findViewById(R.id.create_comment);


        Log.d("Testing comment", comment.toString());

        mCreate = (Button)findViewById(R.id.createC);
        mCreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new cComment().execute();
    }

    class cComment extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(CreateComment.this);
            progressDialog.setMessage("Posting comment....");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args){
            int success;

            String com = comment.getText().toString();
            String username = appuser;
            String soc=society;
            String titleStr = title;


            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("society",soc));
                params.add(new BasicNameValuePair("comment",com));
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("title", titleStr));


                Log.d("request!","starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        CREATE_COMMENT_URL, "POST", params
                );

                if(json!=null){
                    Log.d("Attempting to create.", json.toString());

                    success = json.getInt(TAG_SUCCESS);

                    if(success == 1){
                        Log.d("Comment Posted!", json.toString());
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
            Intent intent = new Intent(getApplicationContext(),ThreadPageActivity.class).putExtra(Intent.EXTRA_TEXT, title);;
            startActivity(intent);
        }
    }
}
