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
import android.widget.TextView;


public class AboutPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AboutPageFragment())
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AboutPageFragment extends Fragment {

        public AboutPageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about_page, container, false);

            String message ="Hey welcome to our app. This app was made by Paul Sugrue" +
                    " and Dave O'Reilly as part of our 3rd Year Project for our college " +
                    "course Computer Applications in DCU. We hope you are enjoying the " +
                    "app and finding it useful.\n\n If you would have any recommendations " +
                    "or would like to get in touch with us, please email us at dcuclubsocapp@gmail.com " +
                    "and we will reply to you as soon as possible. ";

            TextView textView = (TextView) rootView.findViewById(R.id.about);
            textView.setText(message);

            return rootView;
        }
    }
}
