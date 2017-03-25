package com.sloydev.remember.domain

interface ReminderRepository {
    fun getReminders(): List<Reminder>
    fun addReminder(reminder: Reminder)
}

