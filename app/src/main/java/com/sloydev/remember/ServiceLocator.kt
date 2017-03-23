package com.sloydev.remember


object ServiceLocator {
    fun remindersRepository(): RemindersRepository {
        return StubRemindersRepository()
    }
}