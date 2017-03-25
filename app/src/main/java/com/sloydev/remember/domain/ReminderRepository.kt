package com.sloydev.remember.domain

interface ReminderRepository {
    suspend fun getReminders(): List<Reminder>
    fun addReminder(reminder: Reminder)
}

