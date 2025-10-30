package com.example.agecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDate: Button = findViewById(R.id.BtnDate)

        btnDate.setOnClickListener {
            clickDatePicker()
        }

        tvSelectedDate = findViewById(R.id.tvSelectedDay)
        tvAgeInMinutes = findViewById(R.id.tvDateInMinutes)
    }

    private fun clickDatePicker() {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(
                    this,
                    "${selectedDay}/${selectedMonth + 1}/${selectedYear} Selected",
                    Toast.LENGTH_SHORT
                ).show()
                val selectedDay = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                tvSelectedDate?.text = selectedDay
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDay)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes


                        tvAgeInMinutes?.text = differenceInMinutes.toString() + " Min"
                    }

                }


            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}