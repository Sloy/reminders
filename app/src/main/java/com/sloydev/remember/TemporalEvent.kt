package com.sloydev.remember

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TemporalEvent(val name: String, val date: LocalDate, val time: LocalTime? = null) {

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
}