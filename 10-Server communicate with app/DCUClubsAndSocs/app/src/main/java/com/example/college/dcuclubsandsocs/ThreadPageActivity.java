package com.example.college.dcuclubsandsocs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ThreadPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ThreadPageFragment())
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
    public static class ThreadPageFragment extends Fragment {

        private String threadTitle;

        public ThreadPageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent=getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_thread_page, container, false);
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){
                threadTitle=intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById((R.id.threadTitle))).setText(threadTitle);
            }

            String[] comments={
                    "Comment 1","Comment 2","Comment 3","Comment 4","Comment 5","Comment 6","Comment 7"
            };

            List<String> commentList= new ArrayList<>(Arrays.asList(comments));

            ArrayAdapter<String> threadComments = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.sample_comments,
                    R.id.list_comments_textview,
                    commentList
            );

            ListView listView = (ListView) rootView.findViewById(R.id.listview_comments);
            listView.setAdapter(threadComments);



            return rootView;
        }
    }
}
