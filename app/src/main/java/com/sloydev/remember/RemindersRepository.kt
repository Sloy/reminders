package com.sloydev.remember

import org.threeten.bp.LocalDate
import org.threeten.bp.Month

interface RemindersRepository {
    fun getEvents(): List<TemporalEvent>
}

class StubRemindersRepository : RemindersRepository {

    override fun getEvents(): List<TemporalEvent> {
        return listOf(
                DateEvent(name = "Birthday", date = LocalDate.of(1992, Month.JANUARY, 3)),
                DateEvent(name = "Barcelona", date = LocalDate.of(2014, Month.JUNE, 20)),
                DateEvent(name = "InfoJobs", date = LocalDate.of(2015, Month.OCTOBER, 19))
        )
    }
}

