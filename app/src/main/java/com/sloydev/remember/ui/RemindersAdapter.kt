package com.sloydev.remember.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.infrastructure.TimeMachine


class RemindersAdapter(val timeMachine: TimeMachine) : RecyclerView.Adapter<ReminderViewHolder>() {

    var reminders: List<Reminder> = emptyList()

    fun updateReminders(newReminders: List<Reminder>) {
        reminders = newReminders
        notifyDataSetChanged()
    }

    override fun getItemCount() = reminders.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder.create(parent, timeMachine)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.render(reminders[position])
    }
}