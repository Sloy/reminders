package com.sloydev.remember.data

import com.google.firebase.database.FirebaseDatabase
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.domain.ReminderRepository


class FirebaseReminderRepository(val database: FirebaseDatabase) : ReminderRepository {

    override fun addReminder(reminder: Reminder) {
        getReference()
                .child(reminder.name)
                .setValue(reminder.toFirebaseModel())
    }

    override suspend fun getReminders(): List<Reminder> {
        return getReference()
                .getValue()
                .children
                .map { it.getValue(ReminderFirebaseModel::class.java) }
                .map { it.toDomain() }
                .toList()
    }

    private fun getReference() = database.reference.child("users").child("rafa")

}
