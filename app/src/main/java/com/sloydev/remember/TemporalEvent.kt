package com.sloydev.remember

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TemporalEvent(val name: String, val date: LocalDate, val time: LocalTime? = null) {

    fun getDisplayDate() = date.toString()

    fun getDisplayTime() = time?.toString()

}