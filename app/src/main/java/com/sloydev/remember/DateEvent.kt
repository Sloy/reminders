package com.sloydev.remember

import org.threeten.bp.LocalDate

data class DateEvent(val name: String, val date: LocalDate) {

    fun getDisplayDate() = date.toString()

}