package com.sloydev.remember

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_reminders.*
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

class RemindersActivity : AppCompatActivity() {

    lateinit var remindersRepository: RemindersRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        remindersRepository = ServiceLocator.remindersRepository()

        printEvents()
    }

    private fun printEvents() {
        remindersRepository.getEvents()
                .forEach {
                    when (it) {
                        is DateEvent -> showDateEventInView(it)
                    }
                }
    }

    private fun showDateEventInView(dateEvent: DateEvent) {
        val now = LocalDate.now()
        val datePassed = Period.between(dateEvent.date, now)

        val reminderView = LayoutInflater.from(this).inflate(R.layout.item_reminder, remindersContainer, false)
        val nameView = reminderView.findViewById(R.id.item_reminder_name) as TextView
        val dateView: TextView = reminderView.findViewById(R.id.item_reminder_date) as TextView
        val datePassedView: TextView = reminderView.findViewById(R.id.item_reminder_date_passed) as TextView

        nameView.text = dateEvent.name
        dateView.text = dateEvent.getDisplayDate()
        datePassedView.text = "${datePassed.years} years, ${datePassed.months} months, ${datePassed.days} days"

        remindersContainer.addView(reminderView)
    }

}
