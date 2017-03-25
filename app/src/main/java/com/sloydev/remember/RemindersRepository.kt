package com.sloydev.remember

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.Month

interface RemindersRepository {
    fun getEvents(): List<TemporalEvent>
}

class StubRemindersRepository : RemindersRepository {

    override fun getEvents(): List<TemporalEvent> {
        return listOf(
                TemporalEvent(name = "Birthday", date = LocalDate.of(1992, Month.JANUARY, 3)),
                TemporalEvent(name = "Barcelona", date = LocalDate.of(2014, Month.JUNE, 20), time = LocalTime.of(10, 10)),
                TemporalEvent(name = "InfoJobs", date = LocalDate.of(2015, Month.OCTOBER, 19))
        )
    }
}

fun main(args: Array<String>) {
    val temporalEvent = StubRemindersRepository().getEvents()[1]
    print(temporalEvent.getDisplayTime())
}
