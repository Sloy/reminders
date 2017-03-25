package com.sloydev.remember.infrastructure

import com.sloydev.remember.data.ReminderRepository
import com.sloydev.remember.data.StubReminderRepository
import com.sloydev.remember.infrastructure.SystemTimeMachine
import com.sloydev.remember.infrastructure.TimeMachine


object ServiceLocator {

    object Configuration {
        var reminderRepositoryProvider: () -> ReminderRepository = {
            StubReminderRepository()
        }
        var timeMachineProvider: () -> TimeMachine = {
            SystemTimeMachine()
//            HardcodedTimeMachine()
        }
    }

    fun remindersRepository(): ReminderRepository = Configuration.reminderRepositoryProvider.invoke()

    fun timeMachine(): TimeMachine = Configuration.timeMachineProvider.invoke()
}