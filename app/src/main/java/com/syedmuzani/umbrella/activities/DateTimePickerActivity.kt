package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.syedmuzani.umbrella.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import org.jetbrains.anko.find
import java.util.*


class DateTimePickerActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    lateinit var btDp: Button
    lateinit var btTp: Button
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_picker)
        btDp = find(R.id.bt_datepicker)
        btTp = find(R.id.bt_timepicker)
        tv = find(R.id.tv)

        btDp.setOnClickListener { showDateDialog() }
        btTp.setOnClickListener { showTimeDialog() }
    }

    private fun showDateDialog() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show(fragmentManager, "DatePickerDialog")
    }

    private fun showTimeDialog() {
        val now = Calendar.getInstance()
        val dpd = TimePickerDialog.newInstance(
                this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
        )
        dpd.show(fragmentManager, "DatePickerDialog")
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        val time = "You picked the following time: " + hourOfDay + "h" + minute + "m" + second + "s"
        tv.setText(time)
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
        tv.setText(date)
    }
}
