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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.college.dcuclubsandsocs.Login.appuser;


public class MessageInbox extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MessageInboxFragment())
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MessageInboxFragment extends Fragment {

        ProgressDialog pDialog;
        JSONParser jsonParser = new JSONParser();
        //TODO
        private static final String SEND_MESSAGES_URL =
                "http://52.16.161.96/getMessages.php";
        int success;
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        public static List<String> listMyMessages;
        ArrayAdapter<String> messagesReceived;

        public MessageInboxFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_message_inbox, container, false);
            Context context = getActivity();
            listMyMessages=new ArrayList<>();

            new getMyMessages(context,rootView).execute();

            return rootView;
        }


        class getMyMessages extends AsyncTask<String,String,String> {

            private Context mContext;
            private View rootView;

            public getMyMessages(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Getting messages...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            @Override
            protected String doInBackground(String... args){

                try{

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("username",appuser));

                    JSONObject jsonObject=jsonParser
                            .makeHttpRequest(SEND_MESSAGES_URL, "POST", params);

                    Log.d("Fill list attempt", jsonObject.toString());

                    success = jsonObject.getInt(TAG_SUCCESS);

                    if(success == 1 ) {
                        JSONArray array = jsonObject.getJSONArray("posts");
                        Log.d("Array Contents", array.toString());
                        int len = array.length();
                        String len2 = Integer.toString(len);
                        Log.d("Array Length", len2);
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject a = array.getJSONObject(i);
                            Log.d("Test of A", a.toString());
                            String str = a.getString("sender");
                            String str2 = a.getString("message");
                            String time = a.getString("time");
                            str = "From: " + str;
                            str2= "Message: " + str2;
                            time = "Time Sent: : " + time;

                            String str4 =str + "\n\n" + str2 + "\n\n" + time;
                            Log.d("Str2", str2);


                            listMyMessages.add(str4);
                        }

                        return jsonObject.getString(TAG_MESSAGE);
                    }
                    else{
                        Log.d("Failed to update list", jsonObject.getString(TAG_MESSAGE));
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
                    Collections.reverse(listMyMessages);
                    messagesReceived = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.sample_messages,
                            R.id.list_comments_messages,
                            listMyMessages);

                    ListView listView = (ListView)rootView.findViewById(R.id.listview_MessageInbox);
                    listView.setAdapter(messagesReceived);
                }
            }

        }
    }
}
