package com.sloydev.remember


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