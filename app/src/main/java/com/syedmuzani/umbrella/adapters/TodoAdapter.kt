package com.syedmuzani.umbrella.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.syedmuzani.umbrella.activities.ToDoDatabasedActivity
import com.syedmuzani.umbrella.utils.MyDatabaseOpenHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find


class TodoAdapter(private val database: MyDatabaseOpenHelper, private val items:
    List<ToDoDatabasedActivity.TodoItem>): Adapter<TodoAdapter.ViewHold>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        Log.d(TAG, "items: ${items.size}")
        holder.bindItems(database, items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHold(view: View) : RecyclerView.ViewHolder(view) {
        private val tv: TextView = view.find(android.R.id.text1)
        private val context = itemView.context

        fun bindItems(database: MyDatabaseOpenHelper, item: ToDoDatabasedActivity.TodoItem) {
            Log.d(TAG, "item ${item.id}: ${item.name}")
            tv.text = item.name
            tv.setOnLongClickListener {
                doAsync {
                    database.use {
                        val id = item.id
                        delete(MyDatabaseOpenHelper.TODO_TABLE,
                                "${MyDatabaseOpenHelper.COL_ID}='$id'", null)
                    }
                }
                true
            }
        }
    }

    companion object {
        private const val TAG = "TodoAdapter"
    }
}