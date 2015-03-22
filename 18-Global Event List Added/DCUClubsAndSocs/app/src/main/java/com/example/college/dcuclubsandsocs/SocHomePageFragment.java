package com.example.college.dcuclubsandsocs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by College on 14/03/2015.
 */
public class SocHomePageFragment extends Fragment {

    private static final String THREADS_URL = "http://52.16.161.96/sendThreads.php";
    int success;
    TextView textView;
    JSONParser jsonParser = new JSONParser();
    public static String society;

    String[] str;
    int counter = 0;
    public List<String> threadList;
    public List<String> makerList;
    ArrayAdapter<String> threads;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Context context;


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


        context=getActivity();
        threadList = new ArrayList<>();
        makerList = new ArrayList<>();
        new getData(context,rootView).execute();
        return rootView;


    }

    @Override
    public void onResume(){
        super.onResume();
    }

    class getData extends AsyncTask<String,String,String> {

        private Context mContext;
        private View rootView;

        public getData(Context context,View rootView){
            this.mContext=context;
            this.rootView=rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting threads...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args){

            String soc = society;

            try{

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("society",soc));

                Log.d("Params passed", soc);

                JSONObject jsonObject=jsonParser.makeHttpRequest(THREADS_URL, "POST", params);

                Log.d("Fill list attempt", jsonObject.toString());
                success = jsonObject.getInt(TAG_SUCCESS);

                if(success == 1 ) {
                    JSONArray array = jsonObject.getJSONArray("posts");

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject a = array.getJSONObject(i);
                        String str2 = a.getString("title");
                        String str3 = a.getString("user");

                        threadList.add(str2);
                        makerList.add(str3);
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
                Collections.reverse(makerList);
                threads = new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.sample_threads,
                        R.id.list_threads_textview,
                        threadList);

                ListView listView3 = (ListView)rootView.findViewById(R.id.listview_threads);
                listView3.setAdapter(threads);


                Button tCreate;
                tCreate = (Button)rootView.findViewById(R.id.create_threadPage);
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
                        String maker  = makerList.get(position);
                        Bundle extras = new Bundle();
                        Intent intent = new Intent(
                                getActivity(),
                                ThreadPageActivity.class
                        );
                        extras.putString("Title", title);
                        extras.putString("Maker",maker);
                        intent.putExtras(extras);

                        startActivity(intent);
                    }
                });


            }

        }

    }

}
