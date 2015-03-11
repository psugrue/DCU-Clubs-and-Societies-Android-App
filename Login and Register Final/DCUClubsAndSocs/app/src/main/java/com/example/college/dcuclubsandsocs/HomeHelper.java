package com.example.college.dcuclubsandsocs;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HomeHelper {
    public static void getListViewSize(ListView myListView){
        ListAdapter myListAdapter = myListView.getAdapter();
        if(myListAdapter==null){
            return;
        }
        int totalHeight = 0;
        for(int size = 0;size < myListAdapter.getCount();size++){
            View listItem = myListAdapter.getView(size,null,myListView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight()*(myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        Log.i("height of listItem: ", String.valueOf(totalHeight));
    }
}
