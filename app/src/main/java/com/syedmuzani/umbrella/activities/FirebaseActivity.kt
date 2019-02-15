package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.database.*
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.sql.Timestamp
import java.util.*

/** This activity tries to access Firebase, modify two timers on the server, and shows a different
 * interface before, during, and after both timers. It was designed for a bidding app, which
 * would only enable bidding if the current time was after start and before the end timer. **/
class FirebaseActivity : AppCompatActivity() {

    lateinit var textStatus: TextView
    lateinit var tStart: Timestamp
    lateinit var tEnd: Timestamp
    lateinit var tNow: Timestamp

    /** Countdown timer that restarts every time server time changes **/
    var timer: CountDownTimer = object : CountDownTimer(0, 1000) {

        override fun onTick(millisLeft: Long) {
        }

        override fun onFinish() {
        }
    }

    /**
     * Holds a single block of start/end timestamps for this, in the form that can be understood
     * by Firebase.
     *
     * Warning: Calendar takes timestamps in milliseconds, whereas the website handles time
     * in seconds.
     **/
    internal class FirebaseTimestamp {
        var start: Long = 0
        var end: Long = 0

        @Exclude
        fun toMap(): Map<String, Any> {
            val result = HashMap<String, Any>()
            result.put("start", start)
            result.put("end", end)
            return result
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        textStatus = find(R.id.tv_status)
        setupDatetime()
    }

    /** Sets up the datetime on the Firebase server **/
    private fun setupDatetime() {
        val now: Long = Calendar.getInstance().timeInMillis
        val startTime = now / 1000 + START_AFTER_NOW
        val endTime = now / 1000 + END_AFTER_NOW

        val database = FirebaseDatabase.getInstance()
        val refDate = database.getReference("datetime")
        refDate.child("start").setValue(startTime)
        refDate.child("end").setValue(endTime)
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

    /**
     * Displays the data whenever the start and end time on the listener is changed.
     * This will show different interfaces based on whether the time is before, after, or during
     * the two timers
     **/
    private fun displayData(dataSnapshot: DataSnapshot) {
        val timer: FirebaseTimestamp = dataSnapshot.getValue(FirebaseTimestamp::class.java)!!
        tStart = Timestamp(timer.start * 1000)
        tEnd = Timestamp(timer.end * 1000)
        tNow = Timestamp(System.currentTimeMillis())

        if (tNow < tStart) {
            showTimeBeforeStart()
        } else if (tNow >= tStart && tNow < tEnd) {
            showTimeOngoing()
        } else {
            showTimeAfterEnd()
        }
    }

    /** Shows an interface if the timer has not started **/
    private fun showTimeBeforeStart() {
        val millisLeft = tStart.time - tNow.time
        timer.cancel()
        timer = object : CountDownTimer(millisLeft, 1000) {

            override fun onTick(millisLeft: Long) {
                val timerText = "Seconds to start: " + millisLeft / 1000
                val statusText = "Time has not yet started\n$timerText"
                textStatus.text = statusText
            }

            override fun onFinish() {
                showTimeOngoing()
            }
        }.start()
    }

    /** Shows an interface if the timer has started but hasn't ended **/
    private fun showTimeOngoing() {
        val millisLeft = tEnd.time - tNow.time
        timer.cancel()
        timer = object : CountDownTimer(millisLeft, 1000) {

            override fun onTick(millisLeft: Long) {
                val timerText = "Seconds to end: " + millisLeft / 1000
                val statusText = "Time has started\n$timerText"
                textStatus.text = statusText
            }

            override fun onFinish() {
                showTimeAfterEnd()
            }
        }.start()
    }

    /** Shows an interface after the timer has ended **/
    private fun showTimeAfterEnd() {
        textStatus.text = R.string.firebase_time_ended.toString()
    }

    /** Test function to check if Firebase connection is set up properly **/
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
        const val START_AFTER_NOW = 30 // Start in half a minute
        const val END_AFTER_NOW = 60 // End in a minute
    }
}
