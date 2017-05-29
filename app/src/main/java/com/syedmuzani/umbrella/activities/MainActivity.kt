package com.syedmuzani.umbrella.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.AdapterView
import android.widget.ListView
import com.syedmuzani.umbrella.R
import com.syedmuzani.umbrella.adapters.MainPageListAdapter
import com.syedmuzani.umbrella.models.MainMenuLink
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var activity: Activity = this
    internal var links: MutableList<MainMenuLink> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val lv = findViewById(R.id.lv) as ListView
        initListView()
        lv.adapter = MainPageListAdapter(activity, links)
        lv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val link = links[position]
            val bundle = Bundle()
            val intent = Intent(activity, link.activityClass)
            startActivity(intent)
        }

    }

    private fun initListView() {
        links.add(MainMenuLink("Facebook Login", LoginActivity::class.java))
    }
}
