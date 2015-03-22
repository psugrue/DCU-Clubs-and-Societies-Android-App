package com.example.college.dcuclubsandsocs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;



public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
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


        if (id==R.id.action_listAll){
            startActivity(new Intent(this, ListSocs.class));
        }
        if (id==R.id.action_home){
            startActivity(new Intent(this, HomePageActivity.class));
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
        if(id == R.id.action_events){
            startActivity(new Intent(this, EventPage.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment implements View.OnClickListener {

        private static final String LOG_TAG =DetailFragment.class.getSimpleName();
        private static final String SOCIETY_SHARE_HASHTAG= "#DCUClubsSocsApp";
        private String societyStr,societyStr2;
        public Button mEmail;
        public static String info;



        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent=getActivity().getIntent();
            Bundle extras = intent.getExtras();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(intent!=null){
                societyStr=extras.getString("Society");
                info = extras.getString("Email");

                societyStr2= "Welcome to " + societyStr + " contact page.\nPlease " +
                        "find necessary contact information below.\n";
                ((TextView) rootView.findViewById(R.id.detail_text)).setText(societyStr2);
                String str = "Email: " + info + "\n\n\n";
                ((TextView)rootView.findViewById(R.id.detail_email)).setText(str);
            }

            if(info.equals("NO INFO")){
                rootView.findViewById(R.id.sendEmail).setVisibility(rootView.GONE);
            }

            else{
                mEmail = (Button)rootView.findViewById(R.id.sendEmail);
                mEmail.setOnClickListener(this);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
            inflater.inflate(R.menu.detailfragment, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);

            ShareActionProvider mShareActionProvider =
                     (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if(mShareActionProvider!=null){
                mShareActionProvider.setShareIntent(createShareSocietyIntent());
            }
            else{
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareSocietyIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,info);
            return shareIntent;
        }

        @Override
        public void onClick(View v) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{info} );
            startActivity(emailIntent);
        }
    }
}
