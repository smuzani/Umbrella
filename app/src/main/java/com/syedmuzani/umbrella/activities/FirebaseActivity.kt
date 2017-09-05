package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.syedmuzani.umbrella.R
import com.syedmuzani.umbrella.models.FirebaseTimestamp
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.sql.Timestamp


class FirebaseActivity : AppCompatActivity() {

    lateinit var tvStatus: TextView
    lateinit var tStart: Timestamp
    lateinit var tEnd: Timestamp
    lateinit var tNow: Timestamp

    // Countdown timer that reinitializes every time server time changes
    var timer: CountDownTimer = object : CountDownTimer(0, 1000) {

        override fun onTick(millisLeft: Long) {
        }

        override fun onFinish() {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        tvStatus = find(R.id.tv_status)
        setupDatetime()
    }

    private fun setupDatetime() {
        val database = FirebaseDatabase.getInstance()
        val refDate = database.getReference("datetime")
        refDate.child("start").setValue(1507630210)
        refDate.child("end").setValue(1507637532)
        refDate.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                displayData(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                toast("Failed to read value: " + error.toException())
            }
        })
    }

    private fun displayData(dataSnapshot: DataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        val timer = dataSnapshot.getValue(FirebaseTimestamp::class.java)
        tStart = Timestamp(timer.start * 1000)
        tEnd = Timestamp(timer.end * 1000)
        tNow = Timestamp(System.currentTimeMillis())

        if (tNow < tStart) {
            showTimeToStart()
        } else if (tNow >= tStart && tNow < tEnd) {
            showTimeToEnd()
        } else {
            showTimeAfterEnd()
        }
    }

    private fun showTimeToStart() {
        val millisLeft = tStart.time - tNow.time
        timer.cancel()
        timer = object: CountDownTimer(millisLeft, 1000) {

            override fun onTick(millisLeft: Long) {
                val timerText = "Seconds to start: " + millisLeft / 1000
                tvStatus.text = "Time has not yet started\n$timerText"
            }

            override fun onFinish() {
                showTimeToEnd()
            }
        }.start()
    }

    private fun showTimeToEnd() {
        val millisLeft = tEnd.time - tNow.time
        timer.cancel()
        timer = object : CountDownTimer(millisLeft, 1000) {

            override fun onTick(millisLeft: Long) {
                val timerText = "Seconds to end: " + millisLeft / 1000
                tvStatus.text = "Time is happening\n$timerText"
            }

            override fun onFinish() {
                showTimeAfterEnd()
            }
        }.start()
    }

    private fun showTimeAfterEnd() {
        tvStatus.text = "Time ended"
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
