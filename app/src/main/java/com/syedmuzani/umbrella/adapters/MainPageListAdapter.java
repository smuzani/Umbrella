package com.syedmuzani.umbrella.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syedmuzani.umbrella.R;
import com.syedmuzani.umbrella.models.MainMenuLink;

import java.util.List;


public class MainPageListAdapter extends BaseAdapter {

    private static final String TAG = "MainPageListAdapter";

    private Activity activity;
    private List<MainMenuLink> links;

    public MainPageListAdapter(Activity activity, List<MainMenuLink> links) {
        this.activity = activity;
        this.links = links;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = activity.getLayoutInflater().inflate(R.layout.list_item, null);
        MainMenuLink link = links.get(position);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(link.getTitle());
        return view;
    }
}