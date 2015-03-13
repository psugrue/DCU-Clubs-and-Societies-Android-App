package com.example.college.dcuclubsandsocs;

/**
 * Created by Paul on 12/03/2015.
 */

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadThreads extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // php read comments script

    // localhost :
    // testing on your device
    // put your local ip instead, on windows, run CMD > ipconfig
    // or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String READ_COMMENTS_URL =
    // "http://xxx.xxx.x.x:1234/webservice/comments.php";

    // testing on Emulator:
    private static final String READ_COMMENTS_URL = "http://136.206.202.123/webservice2/sendThreads.php";

    // testing from a real server:
    // private static final String READ_COMMENTS_URL =
    // "http://www.mybringback.com/webservice/comments.php";

    // JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TITLE = "title";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_POST_ID = "post_id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_MESSAGE = "message";
    // it's important to note that the message is both in the parent branch of
    // our JSON tree that displays a "Post Available" or a "No Post Available"
    // message,
    // and there is also a message for each individual post, listed under the
    // "posts"
    // category, that displays what the user typed as their message.

    // An array of all of our comments
    private JSONArray mThreads = null;
    // manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mThreadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ReadThreads.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        // note that use read_comments.xml instead of our single_post.xml
        setContentView(R.layout.read_threads);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        new LoadThreads().execute();
    }

    public void addThread(View v) {
        Intent i = new Intent(ReadThreads.this, CreateThread.class);
        startActivity(i);
    }

    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata() {

        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..

        mThreadList = new ArrayList<HashMap<String, String>>();

        // Bro, it's time to power up the J parser
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        // back a JSON object. Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {

            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            mThreads = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < mThreads.length(); i++) {
                JSONObject c = mThreads.getJSONObject(i);

                // gets the content of each tag
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_MESSAGE);
                String username = c.getString(TAG_USERNAME);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_TITLE, title);
                map.put(TAG_MESSAGE, content);
                map.put(TAG_USERNAME, username);

                // adding HashList to ArrayList
                mThreadList.add(map);

                // annndddd, our JSON data is up to date same with our array
                // list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts the parsed data into the listview.
     */
    private void updateList() {
        // For a ListActivity we need to set the List Adapter, and in order to do
        //that, we need to create a ListAdapter.  This SimpleAdapter,
        //will utilize our updated Hashmapped ArrayList,
        //use our single_post xml template for each item in our list,
        //and place the appropriate info from the list to the
        //correct GUI id.  Order is important here.
        ListAdapter adapter = new SimpleAdapter(this, mThreadList,
                R.layout.single_post, new String[] { TAG_TITLE, TAG_MESSAGE,
                TAG_USERNAME }, new int[] { R.id.title, R.id.message,
                R.id.username });

        // I shouldn't have to comment on this one:
        setListAdapter(adapter);

        // Optional: when the user clicks a list item we
        //could do something.  However, we will choose
        //to do nothing...
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // This method is triggered if an item is click within our
                // list. For our example we won't be using this, but
                // it is useful to know in real life applications.

            }
        });
    }

    public class LoadThreads extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadThreads.this);
            pDialog.setMessage("Loading Comments...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
    }
}

