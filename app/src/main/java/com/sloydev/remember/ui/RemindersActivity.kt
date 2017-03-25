package com.sloydev.remember.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sloydev.remember.*
import com.sloydev.remember.data.ReminderRepository
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.infrastructure.ServiceLocator
import com.sloydev.remember.infrastructure.TimeMachine
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : AppCompatActivity() {

    lateinit var reminderRepository: ReminderRepository
    lateinit var timeMachine: TimeMachine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        reminderRepository = ServiceLocator.remindersRepository()
        timeMachine = ServiceLocator.timeMachine()

        printReminders()
    }

    private fun printReminders() {
        reminderRepository.getReminders()
                .forEach {
                    showReminderInView(it)
                }
    }

    @SuppressLint("SetTextI18n")
    private fun showReminderInView(reminder: Reminder) {
        val now = timeMachine.now()

        val reminderView = LayoutInflater.from(this).inflate(R.layout.item_reminder, remindersContainer, false)
        val nameView = reminderView.findViewById(R.id.item_reminder_name) as TextView
        val dateView: TextView = reminderView.findViewById(R.id.item_reminder_date) as TextView
        val datePassedView: TextView = reminderView.findViewById(R.id.item_reminder_date_passed) as TextView
        val timePassedView: TextView = reminderView.findViewById(R.id.item_reminder_time_passed) as TextView

        nameView.text = reminder.name
        dateView.text = reminder.getDisplayDateTime()

        val (years, months, days) = reminder.getDatePassed(now)
        datePassedView.text = "$years years, $months months, $days days"

        val timePassed = reminder.getTimePassed(now)
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
