package com.example.college.dcuclubsandsocs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EventPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EventPageFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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

        if(id == R.id.action_messages){
            startActivity(new Intent(this, MessagesHome.class));
        }
        if (id==R.id.action_home){
            startActivity(new Intent(this, HomePageActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class EventPageFragment extends Fragment {

        ArrayAdapter<String> eventsAdapter;
        private static List<String> eventsList;
        private static List<String> eventInfo;
        private static List<String> eventTime;

        ProgressDialog pDialog,pDialog2;
        JSONParser jsonParser = new JSONParser();
        private static final String SEND_EVENT_LIST_URL =
                "http://52.16.161.96/getEvents.php";
        private static final String UPDATE_EVENT_LIST_URL =
                "http://52.16.161.96/updateEvent.php";
        int success;
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        public static View rootView;
        public static String time;

        public EventPageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
            Context context = getActivity();
            eventsList = new ArrayList<>();
            eventInfo  = new ArrayList<>();
            eventTime  = new ArrayList<>();
            new getEventList(context,rootView).execute();
            return rootView;
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            if (v.getId()==R.id.listview_events) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
                menu.setHeaderTitle(eventInfo.get(info.position));
                time = eventTime.get(info.position);
                String[] menuItems = getResources().getStringArray(R.array.attendEvent);
                for (int i = 0; i<menuItems.length; i++) {
                    menu.add(Menu.NONE, i, i, menuItems[i]);
                }
            }
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            int menuItemIndex = item.getItemId();
            String[] menuItems = getResources().getStringArray(R.array.messageReply);
            if (menuItemIndex==1){
                   Context context2 = getActivity();
                   new updateEvent(context2,rootView,time).execute();
            }
            else if(menuItemIndex==2){
                Intent intent = new Intent(getActivity(),EventPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            return true;
        }



        class getEventList extends AsyncTask<String,String,String> {

            private Context mContext;
            private View rootView;

            public getEventList(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Getting event list...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            @Override
            protected String doInBackground(String... args){

                try{

                    List<NameValuePair> params = new ArrayList<>();

                    JSONObject jsonObject=jsonParser
                            .makeHttpRequest(SEND_EVENT_LIST_URL, "POST", params);

                    Log.d("Fill list attempt", jsonObject.toString());

                    success = jsonObject.getInt(TAG_SUCCESS);

                    if(success == 1 ) {
                        JSONArray array = jsonObject.getJSONArray("posts");
                        Log.d("Array Contents", array.toString());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject a = array.getJSONObject(i);
                            String str = a.getString("society");
                            String str2 = a.getString("title");
                            String str3 = a.getString("message");
                            String str4 = a.getString("time");
                            String str7 = a.getString("number");
                            str7="Number Attending: " + str7;

                            String str5 = str + " : " + str2 + "\n\n-" + str3 + "\n\n" + str7 +
                                    "\nTime Created: " + str4;


                            Log.d("eventsList", str5);
                            eventsList.add(str5);
                            eventInfo.add(str2);
                            eventTime.add(str4);
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
                    Collections.reverse(eventsList);
                    Collections.reverse(eventInfo);
                    Collections.reverse(eventTime);
                    eventsAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            R.layout.list_events,
                            R.id.list_events_textview,
                            eventsList);

                    ListView listView= (ListView) rootView.findViewById(R.id.listview_events);
                    listView.setAdapter(eventsAdapter);

                    Button eCreate;
                    eCreate = (Button)rootView.findViewById(R.id.create_event);
                    eCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(
                                    getActivity(),
                                    CreateEvent.class
                            );
                            startActivity(intent2);
                        }
                    });
                    registerForContextMenu(listView);
                }
            }

        }

        class updateEvent extends AsyncTask<String,String,String> {


            private Context mContext2;
            private View rootView2;
            private String strTime;

            public updateEvent(Context context2, View rootView2, String str){
                this.mContext2=context2;
                this.rootView2=rootView2;
                this.strTime = str;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog2 = new ProgressDialog(getActivity());
                pDialog2.setMessage("Updating event list...");
                pDialog2.setIndeterminate(false);
                pDialog2.setCancelable(true);
                pDialog2.show();
            }


            @Override
            protected String doInBackground(String... args){

                try{
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("time",strTime));

                    JSONObject jsonObject2=jsonParser
                            .makeHttpRequest(UPDATE_EVENT_LIST_URL, "POST", params);

                    success = jsonObject2.getInt(TAG_SUCCESS);

                    if(success == 1 ) {
                        return jsonObject2.getString(TAG_MESSAGE);
                    }
                    else{
                        Log.d("Failed to update list", jsonObject2.getString(TAG_MESSAGE));
                        return jsonObject2.getString(TAG_MESSAGE);
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String file_url){
                pDialog2.dismiss();


                if (success == 1) {
                    Intent intent2 = new Intent(getActivity(),EventPage.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);

                }
            }

        }
    }
}
