package com.sloydev.remember.ui

import com.sloydev.bariksta.activityRule
import com.sloydev.bariksta.assert
import com.sloydev.bariksta.click
import com.sloydev.bariksta.write
import com.sloydev.remember.R
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.infrastructure.ServiceLocator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class NewReminderActivityTest {

    val REMINDER_NAME = "My Event"
    val INVALID_DATE_INPUT = "32132222"
    val VALID_DATE_INPUT = "03011992"
    val VALID_DATE_OUTPUT = "03/01/1992"
    val VALID_DATE_PARSED = LocalDate.of(1992, Month.JANUARY, 3)!!

    @get:Rule
    var activity = activityRule<NewReminderActivity>()

    private val reminderRepository = mock<ReminderRepository>()

    @Before
    fun setUp() {
        ServiceLocator.Configuration.reminderRepositoryProvider = { reminderRepository }
    }

    @Test
    fun activity_opens() {
        activity.launch()

        assert displayed R.id.activity_new_reminder
    }

    @Test
    fun write_date_produces_formatted_date() {
        activity.launch()

        write(VALID_DATE_INPUT) into R.id.addReminderDate

        assert displayed VALID_DATE_OUTPUT
    }

    @Test
    fun invalid_date_shows_error() {
        activity.launch()

        write(INVALID_DATE_INPUT) into R.id.addReminderDate

        assert displayed "Invalid date"
    }

    @Test
    fun event_is_saved_with_input_data() {
        activity.launch()

        write(REMINDER_NAME) into R.id.addReminderName
        write(VALID_DATE_INPUT) into R.id.addReminderDate
        click on R.id.addReminderSaveButton

        val expectedReminder = Reminder(REMINDER_NAME, VALID_DATE_PARSED, null)
        verify(reminderRepository).addReminder(expectedReminder)
    }
}