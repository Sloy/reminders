package com.sloydev.remember.ui

import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.BaristaAssertions.assertDisplayed
import com.schibsted.spain.barista.BaristaClickActions.click
import com.schibsted.spain.barista.BaristaEditTextActions.writeToEditText
import com.sloydev.remember.R
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.infrastructure.ServiceLocator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class NewReminderActivityTest {

    val REMINDER_NAME = "My Event"
    val INVALID_DATE_INPUT = "32132222"
    val VALID_DATE_INPUT = "03011992"
    val VALID_DATE_OUTPUT = "03/01/1992"
    val VALID_DATE_PARSED = LocalDate.of(1992, Month.JANUARY, 3)

    @get:Rule
    var activityRule = ActivityTestRule(NewReminderActivity::class.java, true, false)

    private val reminderRepository = mock(ReminderRepository::class.java)

    @Before
    fun setUp() {
        ServiceLocator.Configuration.reminderRepositoryProvider = fun(): ReminderRepository {
            return reminderRepository
        }
    }

    @Test
    fun activity_opens() {
        activityRule.launchActivity(null)

        assertDisplayed(R.id.activity_new_reminder)
    }

    @Test
    fun write_date_produces_formatted_date() {
        activityRule.launchActivity(null)

        writeToEditText(R.id.addReminderDate, VALID_DATE_INPUT)

        assertDisplayed(VALID_DATE_OUTPUT)
    }

    @Test
    fun invalid_date_shows_error() {
        activityRule.launchActivity(null)

        writeToEditText(R.id.addReminderDate, INVALID_DATE_INPUT)

        assertDisplayed("Invalid date")
    }

    @Test
    fun event_is_saved_with_input_data() {
        activityRule.launchActivity(null)

        writeToEditText(R.id.addReminderName, REMINDER_NAME)
        writeToEditText(R.id.addReminderDate, VALID_DATE_INPUT)
        click(R.id.addReminderSaveButton)

        val expectedReminder = Reminder(REMINDER_NAME, VALID_DATE_PARSED, null)
        verify(reminderRepository).addReminder(expectedReminder)
    }
}