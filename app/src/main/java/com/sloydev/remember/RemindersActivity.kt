package com.sloydev.remember

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : AppCompatActivity() {

    lateinit var remindersRepository: RemindersRepository
    lateinit var timeMachine: TimeMachine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        remindersRepository = ServiceLocator.remindersRepository()
        timeMachine = ServiceLocator.timeMachine()

        printEvents()
    }

    private fun printEvents() {
        remindersRepository.getEvents()
                .forEach {
                    showEventInView(it)
                }
    }

    @SuppressLint("SetTextI18n")
    private fun showEventInView(temporalEvent: TemporalEvent) {
        val now = timeMachine.now()

        val reminderView = LayoutInflater.from(this).inflate(R.layout.item_reminder, remindersContainer, false)
        val nameView = reminderView.findViewById(R.id.item_reminder_name) as TextView
        val dateView: TextView = reminderView.findViewById(R.id.item_reminder_date) as TextView
        val datePassedView: TextView = reminderView.findViewById(R.id.item_reminder_date_passed) as TextView
        val timePassedView: TextView = reminderView.findViewById(R.id.item_reminder_time_passed) as TextView

        nameView.text = temporalEvent.name
        dateView.text = temporalEvent.getDisplayDateTime()

        val (years, months, days) = temporalEvent.getDatePassed(now)
        datePassedView.text = "$years years, $months months, $days days"

        val timePassed = temporalEvent.getTimePassed(now)
        timePassed?.let {
            val (hours, minutes, seconds) = timePassed
            timePassedView.apply {
                text = "$hours hours, $minutes minutes, $seconds seconds"
                visibility = View.VISIBLE
            }
        }

        remindersContainer.addView(reminderView)
    }

}
