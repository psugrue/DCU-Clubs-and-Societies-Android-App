package com.example.college.dcuclubsandsocs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class HomePageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HomePageFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }



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




        return super.onOptionsItemSelected(item);
    }

}
