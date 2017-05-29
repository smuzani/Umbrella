package com.syedmuzani.umbrella.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.syedmuzani.umbrella.R
import com.syedmuzani.umbrella.models.MainMenuLink


class MainPageListAdapter(private val activity: Activity, private val links: List<MainMenuLink>) : BaseAdapter() {

    override fun getCount(): Int {
        return links.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        var v = activity.layoutInflater.inflate(R.layout.list_item, null)
        val link = links[position]
        val tv = view.findViewById(R.id.tv) as TextView
        tv.text = link.title
        return v
    }

    companion object {

        private val TAG = "MainPageListAdapter"
    }
}