package com.sloydev.remember.infrastructure

import com.google.firebase.database.FirebaseDatabase
import com.sloydev.remember.data.FirebaseReminderRepository
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.data.StubReminderRepository
import com.sloydev.remember.infrastructure.SystemTimeMachine
import com.sloydev.remember.infrastructure.TimeMachine


object ServiceLocator {

    object Configuration {
        var reminderRepositoryProvider: () -> ReminderRepository = {
//            StubReminderRepository
            FirebaseReminderRepository(firebaseDatabase())
        }
        var timeMachineProvider: () -> TimeMachine = {
            SystemTimeMachine()
//            HardcodedTimeMachine()
        }
    }

    fun remindersRepository(): ReminderRepository = Configuration.reminderRepositoryProvider.invoke()

    fun timeMachine(): TimeMachine = Configuration.timeMachineProvider.invoke()

    private fun firebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()
}