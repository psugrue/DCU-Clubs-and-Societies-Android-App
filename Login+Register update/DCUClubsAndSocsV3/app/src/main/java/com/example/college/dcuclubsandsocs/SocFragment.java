package com.example.college.dcuclubsandsocs;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocFragment extends Fragment {

    private ArrayAdapter<String> socsAdapter;

    public SocFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.socsfragment, menu);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cas = socsAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, cas);
                startActivity(intent);

            }
        });


        return rootView;
    }

    public class FetchSocTask{

    }
}