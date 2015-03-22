package com.example.college.dcuclubsandsocs;

/**
 * Created by Paul on 09/03/2015.
 */

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.college.dcuclubsandsocs.Login.appuser;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressWarnings("deprecation")
public class HomePageFragment extends Fragment {

    private ArrayAdapter<String> socsAdapter2;
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //URL of PHP file on server
    private static final String SEND_SOC_LIST_URL =
            "http://52.16.161.96/sendSocList.php";
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static List<String> listSocs;

    public HomePageFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.userNameHome);
        textView.setText(appuser);
        listSocs = new ArrayList<String>();
        Context context=getActivity();

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

                Log.d("Fill list attempt", jsonObject.toString());

                success = jsonObject.getInt(TAG_SUCCESS);

                if(success == 1 ) {
                    JSONArray array = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject a = array.getJSONObject(i);
                        String str = a.getString("society");
                        String str2 = a.getString("info");
                        listSocs.add(str);
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

                socsAdapter2 = new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_socs,
                        R.id.list_socs_textview,
                        listSocs);

                ListView listView2= (ListView) rootView.findViewById(R.id.listview_socs2);
                listView2.setAdapter(socsAdapter2);

                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String society = socsAdapter2.getItem(position);
                        Intent intent = new Intent(getActivity(), SocHomePageActivity.class)
                                .putExtra(Intent.EXTRA_TEXT,society);
                        startActivity(intent);
                    }
                });

                TextView mypostsView = (TextView)rootView.findViewById((R.id.home_myposts));
                mypostsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(getActivity(), MyPostsSocsList.class);
                        startActivity(intent2);
                    }
                });

                TextView myThreadsView = (TextView)rootView.findViewById((R.id.home_mythreads));
                myThreadsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent(getActivity(),MyThreadsList.class);
                        startActivity(intent3);
                    }
                });

                TextView myMessagesView = (TextView)rootView.findViewById((R.id.home_messages));
                myMessagesView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent5 = new Intent(getActivity(),MessagesHome.class);
                        startActivity(intent5);
                    }
                });

                Button mSend;
                mSend = (Button)rootView.findViewById(R.id.sendMessage);
                mSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent4 = new Intent(getActivity(),CreateMessage.class);
                        startActivity(intent4);
                    }
                });
            }
        }

    }

}