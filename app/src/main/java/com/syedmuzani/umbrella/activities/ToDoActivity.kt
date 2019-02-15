package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.syedmuzani.umbrella.R
import org.apache.commons.io.FileUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException


/**
 * To Do list based on https://guides.codepath.com/android/Basic-Todo-App-Tutorial
 * TODO: Improve with https://www.sitepoint.com/building-a-ui-with-kotlin-and-anko/
 */

class ToDoActivity : AppCompatActivity() {

    lateinit private var items: ArrayList<String>
    lateinit private var itemsAdapter: ArrayAdapter<String>
    lateinit private var lvItems: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        lvItems = findViewById(R.id.lvItems)
        readItems()
        itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        lvItems.adapter = itemsAdapter
        setupListViewListener()
        setupAddItem()
    }

    fun setupAddItem() {
        val button: Button = find(R.id.btnAddItem)
        button.setOnClickListener {
            val etNewItem: EditText = findViewById(R.id.etNewItem)
            val itemText = etNewItem.text.toString()
            itemsAdapter.add(itemText)
            etNewItem.setText("")
            writeItems()
        }
    }

    private fun setupListViewListener() {
        lvItems.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapter, item, pos, id ->
            items.removeAt(pos)
            itemsAdapter.notifyDataSetChanged()
            writeItems()
            true
        }
    }

    private fun readItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            items = ArrayList<String>(todoFile.readLines()) // Don't even need commons.io
            Log.v("ToDo", "Loaded from file: " + items)
        } catch (e: IOException) {
            items = ArrayList<String>()
            items.add("First Item")
            items.add("Second Item")
            toast("Unable to load items from disk")
        }
    }

    private fun writeItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            FileUtils.writeLines(todoFile, items)
            toast("Saved to file")
        } catch (e: IOException) {
            Log.w("ToDo", e.toString())
            toast("Unable to save items to disk")
        }
    }
}
