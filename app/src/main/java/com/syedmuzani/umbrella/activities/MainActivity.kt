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

/**
 * Central point to visit all other libraries
 */
class MainActivity : AppCompatActivity() {

    internal var activity: Activity = this
    internal var links: MutableList<MainMenuLink> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val rv: RecyclerView = findViewById(R.id.rv)
        initRecyclerView()
        rv.layoutManager = LinearLayoutManager(this)
        val orientation = (rv.layoutManager as LinearLayoutManager).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation)
        rv.addItemDecoration(dividerItemDecoration)
        rv.adapter = MainPageListAdapter(links)
    }

    private fun initRecyclerView() {
        links.add(MainMenuLink("Facebook Login", FacebookLoginActivity::class.java))
        links.add(MainMenuLink("To Do List (File based)", ToDoFileBasedActivity::class.java))
        links.add(MainMenuLink("To Do List (DB based)", ToDoDatabasedActivity::class.java))
        links.add(MainMenuLink("Anko DSL Layouts", DslActivity::class.java))
        links.add(MainMenuLink("VideoView", VideoActivity::class.java))
        links.add(MainMenuLink("Fingerprint", FingerprintActivity::class.java))
        links.add(MainMenuLink("Firebase With Countdown", FirebaseActivity::class.java))
        links.add(MainMenuLink("Date/TimePicker", DateTimePickerActivity::class.java))
    }
}
