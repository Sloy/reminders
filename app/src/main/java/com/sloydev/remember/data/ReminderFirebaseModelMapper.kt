package com.sloydev.remember.data

import com.sloydev.remember.domain.Reminder
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter


val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun Reminder.toFirebaseModel() = mapFromDomain(this)
fun ReminderFirebaseModel.toDomain() = mapToDomain(this)

fun mapFromDomain(domain: Reminder): ReminderFirebaseModel {
    return ReminderFirebaseModel(
            name = domain.name,
            date = dateFormatter.format(domain.date),
            time = domain.time?.let { timeFormatter.format(domain.time) }
    )
}

fun mapToDomain(firebaseModel: ReminderFirebaseModel): Reminder {
    return Reminder(
            name = firebaseModel.name ?: throw IllegalStateException("Received null name"),
            date = LocalDate.parse(firebaseModel.date, dateFormatter),
            time = firebaseModel.time?.let { LocalTime.parse(firebaseModel.time, timeFormatter) }
    )
}