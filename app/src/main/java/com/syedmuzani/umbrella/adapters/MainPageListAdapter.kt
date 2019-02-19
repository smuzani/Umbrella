package com.syedmuzani.umbrella.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.syedmuzani.umbrella.models.MainMenuLink
import org.jetbrains.anko.find


class MainPageListAdapter(private val items: List<MainMenuLink>): Adapter<MainPageListAdapter.ViewHold>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHold(view: View) : RecyclerView.ViewHolder(view) {
        private val tv: TextView = view.find(android.R.id.text1)
        private val context = itemView.context

        fun bindItems(link: MainMenuLink) {
            tv.text = link.title
            tv.setOnClickListener {
                val intent = Intent(context, link.activityClass)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val TAG = "MainPageListAdapter"
    }
}