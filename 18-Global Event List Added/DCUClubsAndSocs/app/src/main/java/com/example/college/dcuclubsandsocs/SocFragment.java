package com.example.college.dcuclubsandsocs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SocFragment extends Fragment {

    ArrayAdapter<String> socsAdapter;
    public static List<String> socsList;
    public static List<String> socsInfo;

    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String SEND_SOC_LIST_URL =
            "http://52.16.161.96/sendSocList.php";
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    public SocFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Context context = getActivity();
        socsInfo= new ArrayList<String>();
        socsList= new ArrayList<String>();
        new getSocList(context,rootView).execute();
        return rootView;
    }


    class getSocList extends AsyncTask<String,String,String> {

        private Context mContext;
        private View rootView;

        public getSocList(Context context, View rootView){
            this.mContext=context;
            this.rootView=rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting society list...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args){

            try{

                List<NameValuePair> params = new ArrayList<>();

                JSONObject jsonObject=jsonParser
                        .makeHttpRequest(SEND_SOC_LIST_URL, "POST", params);

                success = jsonObject.getInt(TAG_SUCCESS);

                if(success == 1 ) {
                    JSONArray array = jsonObject.getJSONArray("posts");
                    Log.d("Array Contents", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject a = array.getJSONObject(i);
                        String str = a.getString("society");
                        String str2 = a.getString("info");

                        socsList.add(str);
                        socsInfo.add(str2);
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

                socsAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_socs_2,
                        R.id.list_socs_textview4,
                        socsList);

                ListView listView6= (ListView) rootView.findViewById(R.id.listview_socs4);
                listView6.setAdapter(socsAdapter);

                listView6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String society = socsAdapter.getItem(position);
                        String info = socsInfo.get(position);
                        Log.d("Email: ", info);
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("Society", society);
                        extras.putString("Email",info);
                        intent.putExtras(extras);

                        startActivity(intent);
                    }
                });
            }
        }

    }

}