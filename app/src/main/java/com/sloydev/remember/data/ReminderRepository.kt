package com.sloydev.remember.data

import com.sloydev.remember.domain.Reminder
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.Month

interface ReminderRepository {
    fun getReminders(): List<Reminder>
}

class StubReminderRepository : ReminderRepository {

    override fun getReminders(): List<Reminder> {
        return listOf(
                Reminder(name = "Birthday", date = LocalDate.of(1992, Month.JANUARY, 3)),
                Reminder(name = "Barcelona", date = LocalDate.of(2014, Month.JUNE, 20), time = LocalTime.of(10, 10)),
                Reminder(name = "InfoJobs", date = LocalDate.of(2015, Month.OCTOBER, 19))
        )
    }
}
