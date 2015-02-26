package com.example.college.dcuclubsandsocs;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ArrayAdapter<String> socsAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String[] socsArray = {
                    "AIESEC",
                    "Accounting and Finance",
                    "Africa",
                    "Airsoft",
                    "Amnesty International",
                    "An Cumann Gaelach",
                    "An Taisce",
                    "Anime and Manga",
                    "Art Soc",
                    "Aussie Rules",
                    "Biological Research",
                    "Book",
                    "Business Consulting",
                    "Cancer",
                    "Chess Soc",
                    "Chinese",
                    "Christian Union",
                    "Circus Arts"
            };

            List<String> socsList= new ArrayList<String>(Arrays.asList(socsArray));

            //using our sample data above we will now create an ArrayAdapter. This will
            //take data from a source and use it to populate the ListView's its attached to.

            socsAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_socs,
                    R.id.list_socs_textview,
                    socsList);

            ListView listView= (ListView) rootView.findViewById(R.id.listview_socs);
            listView.setAdapter(socsAdapter);


            return rootView;
        }
    }
}
