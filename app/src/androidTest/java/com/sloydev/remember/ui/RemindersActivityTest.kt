package com.sloydev.remember.ui

import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.BaristaAssertions.assertDisplayed
import com.schibsted.spain.barista.BaristaAssertions.assertNotExist
import com.schibsted.spain.barista.BaristaClickActions.click
import com.schibsted.spain.barista.BaristaClickActions.clickBack
import com.sloydev.remember.R
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.infrastructure.ServiceLocator
import com.sloydev.remember.infrastructure.TimeMachine
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.Month
import java.util.Arrays.asList

class RemindersActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(RemindersActivity::class.java, true, false)

    val NOW = LocalDateTime.of(2017, Month.MARCH, 25, 10, 52, 42)!!
    val PASSED = LocalDateTime.of(2016, Month.FEBRUARY, 24, 9, 51, 41)!!

    val REMINDER_WITH_DATE_TIME = Reminder("My reminder", LocalDate.from(PASSED), LocalTime.from(PASSED))
    val FIRST_REMINDER = Reminder("First reminder", LocalDate.from(PASSED), LocalTime.from(PASSED))
    val SECOND_REMINDER = Reminder("Second reminder", LocalDate.from(PASSED), LocalTime.from(PASSED))

    val reminderRepository = mock(ReminderRepository::class.java)!!
    val timeMachine = mock(TimeMachine::class.java)!!

    @Before
    fun setUp() {
        runBlocking {
            `when`<Any>(reminderRepository.getReminders()).thenReturn(emptyList<Any>())
            `when`(timeMachine.now()).thenReturn(NOW)
            ServiceLocator.Configuration.reminderRepositoryProvider = fun(): ReminderRepository {
                return reminderRepository
            }
            ServiceLocator.Configuration.timeMachineProvider = fun(): TimeMachine {
                return timeMachine
            }
        }
    }

    @Test
    fun activity_shows_fake_reminder() {
        runBlocking {
            `when`<Any>(reminderRepository.getReminders()).thenReturn(listOf(REMINDER_WITH_DATE_TIME))

            activityRule.launchActivity(null)

            assertDisplayed("My reminder")
            assertDisplayed("2016-02-24 09:51:41")
            assertDisplayed("1 years, 1 months, 1 days")
            assertDisplayed("1 hours, 1 minutes, 1 seconds")
        }
    }

    @Test
    fun fab_click_opens_editor_screen() {
        runBlocking {
            activityRule.launchActivity(null)

            click(R.id.remindersAddButton)

            assertDisplayed(R.id.activity_new_reminder)
        }
    }

    @Test
    fun reloads_list_after_adding_reminder() {
        runBlocking {
            `when`<Any>(reminderRepository.getReminders()).thenReturn(listOf(FIRST_REMINDER))

            activityRule.launchActivity(null)

            assertDisplayed(FIRST_REMINDER.name)
            assertNotExist(SECOND_REMINDER.name)

            click(R.id.remindersAddButton)
            `when`<Any>(reminderRepository.getReminders()).thenReturn(asList(FIRST_REMINDER, SECOND_REMINDER))
            clickBack()

            assertDisplayed(FIRST_REMINDER.name)
            assertDisplayed(SECOND_REMINDER.name)
        }
    }
}