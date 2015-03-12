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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SocHomePageActivity extends ActionBarActivity{


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
    public static class SocHomePageFragment extends Fragment {


        private String society;

        public SocHomePageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent=getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_soc_home_page, container, false);
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){
                society=intent.getStringExtra(Intent.EXTRA_TEXT);

                ((TextView) rootView.findViewById(R.id.socHomeTitle)).setText(society);
            }
          //  fCreate=(Button)findViewById(R.id.create_threadPage);


            String[] threads={
                    "Thread 1", "Thread2","Thread3", "Thread4","Thread5"
            };

            List<String> threadList= new ArrayList<String>(Arrays.asList(threads));

            final ArrayAdapter<String> socthreads = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.sample_threads,
                    R.id.list_threads_textview,
                    threadList
            );

            ListView listView2= (ListView) rootView.findViewById(R.id.listview_threads);
            listView2.setAdapter(socthreads);

            Button tCreate;
            tCreate=(Button)rootView.findViewById(R.id.create_threadPage);
            tCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(
                            getActivity(),
                            CreateThread.class
                    );
                    startActivity(intent2);
                }
            });


            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String title = socthreads.getItem(position);
                    Intent intent = new Intent(
                            getActivity(),
                            ThreadPageActivity.class
                    ).putExtra(Intent.EXTRA_TEXT,title);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }
}
