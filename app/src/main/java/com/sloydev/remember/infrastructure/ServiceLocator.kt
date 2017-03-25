package com.sloydev.remember.infrastructure

import com.sloydev.remember.data.RemindersRepository
import com.sloydev.remember.data.StubRemindersRepository
import com.sloydev.remember.infrastructure.SystemTimeMachine
import com.sloydev.remember.infrastructure.TimeMachine


object ServiceLocator {

    object Configuration {
        var remindersRepositoryProvider: () -> RemindersRepository = {
            StubRemindersRepository()
        }
        var timeMachineProvider: () -> TimeMachine = {
            SystemTimeMachine()
//            HardcodedTimeMachine()
        }
    }

    fun remindersRepository(): RemindersRepository = Configuration.remindersRepositoryProvider.invoke()

    fun timeMachine(): TimeMachine = Configuration.timeMachineProvider.invoke()
}