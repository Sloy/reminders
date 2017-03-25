package com.sloydev.remember.domain

import org.threeten.bp.*

data class Reminder(val name: String, val date: LocalDate, val time: LocalTime? = null) {

    fun getDisplayDate() = date.toString()

    fun getDisplayTime() = time?.toString()

    fun getDisplayDateTime(): String {
        time?.let {
            return "${getDisplayDate()} ${getDisplayTime()}"
        } ?: return getDisplayDate()
    }

    fun getDisplayDateTimeJava(): String {
        if (time == null) {
            return getDisplayDate();
        } else {
            return String.format("%s %s", getDisplayDate(), getDisplayTime());
        }
    }

    fun getDatePassed(now: LocalDateTime): DatePassed {
        val periodPassed = Period.between(date, now.toLocalDate())
        return DatePassed(periodPassed.years, periodPassed.months, periodPassed.days)
    }

    fun getTimePassed(now: LocalDateTime): TimePassed? {
        time?.let {
            val between = Duration.between(LocalDateTime.of(date, time), now)
            return TimePassed(
                    hours = (between.toHours() % 24L).toInt(),
                    minutes = (between.toMinutes() % 60L).toInt(),
                    seconds = (between.seconds % 60L).toInt())
        } ?: return null
    }

    data class DatePassed(val years: Int, val months: Int, val days: Int)
    data class TimePassed(val hours: Int, val minutes: Int, val seconds: Int)

}