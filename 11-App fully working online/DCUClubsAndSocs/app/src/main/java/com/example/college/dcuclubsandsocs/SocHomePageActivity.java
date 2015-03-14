package com.example.college.dcuclubsandsocs;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;
import java.util.HashMap;
import java.util.Map;


public class SocHomePageActivity extends ActionBarActivity{

    public static String society;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_home_page);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SocHomePageFragment())
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

        if (id == R.id.action_home) {
            startActivity(new Intent(this, HomePageActivity.class));
            return true;
        }

        if (id==R.id.action_listAll){
            startActivity(new Intent(this, ListSocs.class));
        }

        if(id == R.id.action_aboutApp){
            startActivity(new Intent(this, AboutPageActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class SocHomePageFragment extends Fragment {

        private static final String THREADS_URL = "http://52.16.161.96/sendThreads.php";
        int success;
        TextView textView;
        JSONParser jsonParser = new JSONParser();

        String[] str;
        int counter = 0;
        public List<String> threadList = new ArrayList<>();
        ArrayAdapter<String> threads;
        private ProgressDialog pDialog;
        //public static String society;
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        public SocHomePageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_soc_home_page, container, false);
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                society = intent.getStringExtra(Intent.EXTRA_TEXT);

                ((TextView) rootView.findViewById(R.id.socHomeTitle)).setText(society + " Homepage");
            }

            new getData().execute();


            return rootView;


        }

        @Override
        public void onResume()
        {  // After a pause OR at startup
            super.onResume();
            //Refresh your stuff here
        }

        class getData extends AsyncTask<String,String,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(SocHomePageActivity.this);
                pDialog.setMessage("Getting threads...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            @Override
            protected String doInBackground(String... args){
                //String society2 = textView.getText().toString();

                String soc = society;

                try{

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("society",soc));

                    Log.d("Params passed",soc);

                    JSONObject jsonObject=jsonParser.makeHttpRequest(THREADS_URL, "POST", params);

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
                            String str2 = a.getString("title");
                            Log.d("Str2", str2);
                            threadList.add(str2);
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
                Log.d("End Contents",threadList.toString());

                return null;
            }


            protected void onPostExecute(String file_url) {
                // dismiss the dialog once product deleted
                pDialog.dismiss();

                if (success == 1) {
                    Collections.reverse(threadList);
                    threads = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.sample_threads,
                            R.id.list_threads_textview,
                            threadList);

                    ListView listView3 = (ListView) findViewById(R.id.listview_threads);
                    listView3.setAdapter(threads);


                    Button tCreate;
                    tCreate = (Button) findViewById(R.id.create_threadPage);
                    tCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(
                                    getActivity(),
                                    CreateThread.class
                            ).putExtra(Intent.EXTRA_TEXT, society);
                            startActivity(intent2);
                        }
                    });


                    listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String title = threads.getItem(position);
                            Intent intent = new Intent(
                                    getActivity(),
                                    ThreadPageActivity.class
                            ).putExtra(Intent.EXTRA_TEXT, title);
                            startActivity(intent);
                        }
                    });


                }

            }

        }

    }
}
