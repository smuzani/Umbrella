package com.syedmuzani.umbrella.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.syedmuzani.umbrella.R;
import com.syedmuzani.umbrella.adapters.MainPageListAdapter;
import com.syedmuzani.umbrella.models.MainMenuLink;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Activity activity = this;
    List<MainMenuLink> links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lv = (ListView) findViewById(R.id.lv);
        initListView();
        lv.setAdapter(new MainPageListAdapter(activity, links));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MainMenuLink link = links.get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(activity, link.getActivityClass());
                startActivity(intent);
            }
        });

    }

    private void initListView(){
        links.add(new MainMenuLink("Facebook Login", LoginActivity.class));
    }
}
