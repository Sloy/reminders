package com.sloydev.remember.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sloydev.remember.R
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.infrastructure.TimeMachine
import kotlinx.android.synthetic.main.item_reminder.view.*


class ReminderViewHolder(view: View, val timeMachine: TimeMachine) : RecyclerView.ViewHolder(view) {

    companion object Factory {
        fun create(parent: ViewGroup, timeMachine: TimeMachine): ReminderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
            return ReminderViewHolder(view, timeMachine)
        }
    }

    fun render(reminder: Reminder) {
        val now = timeMachine.now()

        itemView.reminder_name.text = reminder.name
        itemView.reminder_date.text = reminder.getDisplayDateTime()

        val (years, months, days) = reminder.getDatePassed(now)
        itemView.reminder_date_passed.text = "$years years, $months months, $days days"

        val timePassed = reminder.getTimePassed(now)
        timePassed?.let {
            val (hours, minutes, seconds) = timePassed
            itemView.reminder_time_passed.apply {
                text = "$hours hours, $minutes minutes, $seconds seconds"
                visibility = View.VISIBLE
            }
        }
    }
}