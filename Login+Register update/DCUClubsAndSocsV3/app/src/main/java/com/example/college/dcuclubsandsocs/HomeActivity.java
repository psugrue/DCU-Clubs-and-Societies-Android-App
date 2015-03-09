package com.example.college.dcuclubsandsocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;

import helper.SQLiteHandler;
import helper.SessionManager;

/**
 * Created by Dave on 08/03/2015.
 */
public class HomeActivity extends Activity {
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
            "Circus Arts",
            "DJ",
            "Dance",
            "Debate",
            "Drama",
            "Enactus",
            "Engineering",
            "Esoc",
            "FLAC",
            "FemSoc",
            "Fianna Fail",
            "Film",
            "Financial & Actuarial",
            "Food",
            "Fotosoc",
            "French",
            "Friends",
            "Gaisce",
            "Games",
            "Global Brigades",
            "Global Business",
            "Hiking",
            "Horticulture",
            "INTERNATIONAL RELATIONS",
            "Indian Soc",
            "Intercultural",
            "Investment",
            "Islamic",
            "Japanese",
            "Journalism",
            "Karting",
            "LGBTA",
            "Labour",
            "Law",
            "Mature Students",
            "Media Production Society",
            "Music",
            "Nursing",
            "Paintball",
            "Poker",
            "Pool & Snooker",
            "Postgrad",
            "Psychology",
            "RAW - Raising for Animal Welfare",
            "Raising and Giving",
            "Redbrick: Networking Society",
            "Sinn Fein",
            "Snowsports",
            "Sober Society",
            "Society Of Science",
            "St Vincent De Paul",
            "Strange Things",
            "Student Business Consulting",
            "Style",
            "Suas",
            "Tea Soc",
            "Unicef on Campus",
            "Urban Artz",
            "Walk and Talk",
            "Yoga",
            "Young Fine Gael",
            "Aikido",
            "Archery",
            "Athletics",
            "Badminton",
            "Basketball Men",
            "Basketball Women",
            "Boxing Club",
            "Camogie",
            "Canoe",
            "Caving",
            "Clay Target",
            "Cricket",
            "Cycling",
            "Equestrian",
            "Fencing",
            "GAA (M)",
            "GAA (W)",
            "Golf",
            "Gymnastics & Trampolining",
            "Handball",
            "Hockey (Men & Womens)",
            "Horse Racing",
            "Hurling",
            "Jogging",
            "Judo",
            "Karate",
            "Mixed Martial Arts",
            "Rock Climbing",
            "Rowing",
            "Rugby (Men)",
            "Rugby (Women)",
            "Soccer (Men)",
            "Soccer (Women)",
            "Squash",
            "Sub Aqua",
            "Surf 'n' Sail",
            "Swimming/Waterpolo",
            "Table Tennis",
            "Taekwon Do",
            "Tennis",
            "Ultimate Frisbee",
            "Volleyball",
            "Weight Lifting"
    };

    ListView myList;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_detail);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        // Displaying the user details on the screen

        // Logout button click event


        myList = (ListView) findViewById(R.id.listview_socs2);
        myList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                socsArray));
        HomeHelper.getListViewSize(myList);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

