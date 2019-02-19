package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import com.syedmuzani.umbrella.R
import com.syedmuzani.umbrella.adapters.TodoAdapter
import com.syedmuzani.umbrella.utils.MyDatabaseOpenHelper
import com.syedmuzani.umbrella.utils.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.util.*


/**
 * To Do list based on https://guides.codepath.com/android/Basic-Todo-App-Tutorial
 * Saves to file
 * TODO: Improve with https://www.sitepoint.com/building-a-ui-with-kotlin-and-anko/
 */

class ToDoDatabasedActivity : AppCompatActivity() {

    class TodoItem (val id: String, val name: String)

    private lateinit var items: ArrayList<TodoItem>
    private var itemsAdapter: TodoAdapter? = null
    private lateinit var lvItems: RecyclerView
    private lateinit var dbHelper: MyDatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        dbHelper = MyDatabaseOpenHelper.getInstance(this)
        lvItems = findViewById(R.id.lvItems)
        readItems()
    }

    private fun setupAddItem() {
        val button: Button = find(R.id.btnAddItem)
        button.setOnClickListener {
            val etNewItem: EditText = findViewById(R.id.etNewItem)
            val itemText = etNewItem.text.toString()
            doAsync {
                database.use {
                    insert(MyDatabaseOpenHelper.TODO_TABLE,
                            MyDatabaseOpenHelper.COL_ID to UUID.randomUUID().toString().replace("-", ""),
                            MyDatabaseOpenHelper.COL_NAME to itemText)
                    readItems()
                }
            }
            etNewItem.setText("")
        }
    }

    private fun setupListViewListener() {
        lvItems.layoutManager = LinearLayoutManager(this)
        val orientation = (lvItems.layoutManager as LinearLayoutManager).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation)
        lvItems.addItemDecoration(dividerItemDecoration)
        lvItems.adapter = TodoAdapter(database, items)
    }

    private fun readItems() {
        doAsync {
            database.use {
                select(MyDatabaseOpenHelper.TODO_TABLE,
                        MyDatabaseOpenHelper.COL_ID,
                        MyDatabaseOpenHelper.COL_NAME).exec {
                    val rowParser = classParser<TodoItem>()
                    items = ArrayList(parseList(rowParser))
                    if (items.isEmpty()){
                        items = ArrayList()
                        insert(MyDatabaseOpenHelper.TODO_TABLE,
                                MyDatabaseOpenHelper.COL_ID to UUID.randomUUID().toString().replace("-", ""),
                                MyDatabaseOpenHelper.COL_NAME to "First Item")
                        insert(MyDatabaseOpenHelper.TODO_TABLE,
                                MyDatabaseOpenHelper.COL_ID to UUID.randomUUID().toString().replace("-", ""),
                                MyDatabaseOpenHelper.COL_NAME to "Second Item")
                    }

                    if (itemsAdapter == null) {
                        uiThread {
                            setupListViewListener()
                            setupAddItem()
                        }
                    } else {
                        uiThread {
                            itemsAdapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}
