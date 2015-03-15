package com.example.college.dcuclubsandsocs;

/**
 * Created by Paul on 09/03/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.college.dcuclubsandsocs.Login.appuser;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressWarnings("deprecation")
public class HomePageFragment extends Fragment {

    private ArrayAdapter<String> socsAdapter2;

    public HomePageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_home_page, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        String[] socsArray2 = {
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

        List<String> socsList2= new ArrayList<String>(Arrays.asList(socsArray2));

        //using our sample data above we will now create an ArrayAdapter. This will
        //take data from a source and use it to populate the ListView's its attached to.

        socsAdapter2 = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_socs,
                R.id.list_socs_textview,
                socsList2);



        TextView textView = (TextView) rootView.findViewById(R.id.userNameHome);
        textView.setText(appuser);


        ListView listView2= (ListView) rootView.findViewById(R.id.listview_socs2);
        listView2.setAdapter(socsAdapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String society = socsAdapter2.getItem(position);
                //Toast.makeText(getActivity(),society,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SocHomePageActivity.class)
                       .putExtra(Intent.EXTRA_TEXT,society);
                startActivity(intent);
            }
        });

        return rootView;
    }



}