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
import java.util.List;

import static com.example.college.dcuclubsandsocs.SocHomePageFragment.society;



/**
 * Created by College on 14/03/2015.
 */
public class ThreadPageFragment extends Fragment {

    private String threadTitle;
    private String maker;
    private String fullTitle;
    private static final String COMMENTS_URL = "http://52.16.161.96/sendComments.php";
    int success;
    TextView textView;
    JSONParser jsonParser = new JSONParser();
    public static String title;
    String[] str;
    int counter = 0;
    public List<String> commentList;
    public List<String> userList;
    ArrayAdapter<String> comments;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Context context;
    public static String maker2;



    public ThreadPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent=getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_thread_page, container, false);
        Bundle extras = intent.getExtras();
        if(intent!=null){
            threadTitle=extras.getString("Title");
            maker = extras.getString("Maker");
            title=threadTitle;
            maker2=maker;
            fullTitle = threadTitle + "-" + maker;
            ((TextView) rootView.findViewById((R.id.threadTitle))).setText(fullTitle);
        }

        context=getActivity();
        commentList = new ArrayList<>();
        userList = new ArrayList<>();
        new getComments(context,rootView).execute();

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }


    class getComments extends AsyncTask<String,String,String> {

        private Context mContext;
        private View rootView;

        public getComments(Context context, View rootView){
            this.mContext=context;
            this.rootView=rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting comments...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args){

            try{

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("title",title));
                params.add(new BasicNameValuePair("society",society));

                Log.d("Params passed", title + society);

                JSONObject jsonObject=jsonParser.makeHttpRequest(COMMENTS_URL, "POST", params);

                Log.d("Fill list attempt", jsonObject.toString());

                success = jsonObject.getInt(TAG_SUCCESS);

                if(success == 1 ) {
                    JSONArray array = jsonObject.getJSONArray("posts");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject a = array.getJSONObject(i);
                        String str2 = a.getString("message");
                        String str3 = a.getString("user");

                        str2 = str2 + "\n\n-" + str3;
                        commentList.add(str2);
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
            Log.d("End Contents",commentList.toString());

            return null;
        }


        protected void onPostExecute(String file_url){
            pDialog.dismiss();

            if (success == 1) {
                comments = new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.sample_comments,
                        R.id.list_comments_textview,
                        commentList);

                ListView listView4 = (ListView)rootView.findViewById(R.id.listview_comments);
                listView4.setAdapter(comments);


                Button cCreate;
                cCreate = (Button)rootView.findViewById(R.id.create_comment);
                cCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(
                                getActivity(),
                                CreateComment.class
                        );
                        startActivity(intent2);
                    }
                });
            }
        }

    }
}