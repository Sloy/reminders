package com.sloydev.remember.infrastructure

import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month


interface TimeMachine {
    fun now(): LocalDateTime
}

class SystemTimeMachine : TimeMachine {
    override fun now(): LocalDateTime = LocalDateTime.now()
}

class HardcodedTimeMachine : TimeMachine {
    override fun now(): LocalDateTime = LocalDateTime
            .of(2017, Month.MARCH, 25, 10, 52, 42)
}