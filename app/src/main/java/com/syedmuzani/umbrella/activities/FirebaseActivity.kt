package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.syedmuzani.umbrella.R
import com.syedmuzani.umbrella.models.FirebaseTimestamp
import org.jetbrains.anko.toast


class FirebaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        setupDatetime()
    }

    private fun setupDatetime() {
        val database = FirebaseDatabase.getInstance()
        val refDate = database.getReference("datetime")
        refDate.child("start").setValue(1507630210)
        refDate.child("end").setValue(1507637532)
        refDate.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val timer = dataSnapshot.getValue(FirebaseTimestamp::class.java)
                toast("Start: " + timer.start + " and end: " + timer.end)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                toast("Failed to read value: " + error.toException())
            }
        })
    }

    private fun test() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                toast("Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                toast("Failed to read value: " + error.toException())
            }
        })
    }

    companion object {
        val TAG = "Firebase"
    }
}
