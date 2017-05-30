package com.syedmuzani.umbrella.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
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
        val rv = findViewById(R.id.rv) as RecyclerView
        initRecyclerView()
        rv.layoutManager = LinearLayoutManager(this)
        val orientation = (rv.layoutManager as LinearLayoutManager).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation)
        rv.addItemDecoration(dividerItemDecoration)
        rv.adapter = MainPageListAdapter(links)
//        lv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            val link = links[position]
//            val bundle = Bundle()
//            val intent = Intent(activity, link.activityClass)
//            startActivity(intent)
//        }

    }

    private fun initRecyclerView() {
        links.add(MainMenuLink("Facebook Login", LoginActivity::class.java))
//        links.add(MainMenuLink("Facebook Login", LoginActivity::class.java))
    }
}
