package com.example.college.dcuclubsandsocs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.college.dcuclubsandsocs.Login.appuser;
import static com.example.college.dcuclubsandsocs.MyPostsSocsList.MyPostsSocListFragment.comment;
import static com.example.college.dcuclubsandsocs.MyPostsSocsList.MyPostsSocListFragment.time;



public class DeletePost extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DeletePostFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id==R.id.action_home){
            startActivity(new Intent(this, HomePageActivity.class));
        }

        if (id==R.id.action_listAll){
            startActivity(new Intent(this, ListSocs.class));
        }

        if(id == R.id.action_aboutApp){
            startActivity(new Intent(this, AboutPageActivity.class));
        }

        if(id == R.id.action_posts){
            startActivity(new Intent(this, MyPostsSocsList.class));
        }

        if(id == R.id.action_threads){
            startActivity(new Intent(this, MyThreadsList.class));
        }
        if(id == R.id.action_events){
            startActivity(new Intent(this, EventPage.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DeletePostFragment extends Fragment {


        ProgressDialog pDialog;
        JSONParser jsonParser = new JSONParser();
        //TODO
        private static final String DELETE_POST_URL =
                "http://52.16.161.96/deletePost.php";
        int success;
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        public DeletePostFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_delete_post, container, false);
            final Context context=getActivity();

            Button b1= (Button)rootView.findViewById(R.id.yes);
            Button b2 =(Button)rootView.findViewById(R.id.no);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new delPost(context,rootView).execute();
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(getActivity(),MyPostsSocsList.class);
                    startActivity(intent2);
                }
            });

            return rootView;
        }


        class delPost extends AsyncTask<String,String,String> {

            private Context mContext;
            private View rootView;

            public delPost(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Deleting post...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            @Override
            protected String doInBackground(String... args){

                try{

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("username",appuser));
                    params.add(new BasicNameValuePair("comment",comment));
                    params.add(new BasicNameValuePair("time",time));

                    Log.d("username",appuser);
                    Log.d("comment",comment);
                    Log.d("username",time);

                    JSONObject jsonObject=jsonParser
                            .makeHttpRequest(DELETE_POST_URL, "POST", params);

                    Log.d("Fill list attempt", jsonObject.toString());

                    success = jsonObject.getInt(TAG_SUCCESS);

                    if(success == 1){
                        Log.d("Message Sent", jsonObject.toString());
                        //finish();
                        return jsonObject.getString(TAG_MESSAGE);
                    }
                    else{
                        Log.d("Thread Creation Failed", jsonObject.getString(TAG_MESSAGE));
                        return jsonObject.getString(TAG_MESSAGE);
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                return null;
            }


            protected void onPostExecute(String file_url){
                pDialog.dismiss();

                if (success == 1) {
                    Intent intent2 = new Intent(getActivity(),MyPostsSocsList.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                }
            }

        }
    }
}
