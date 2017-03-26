package com.sloydev.remember.ui;

import android.support.test.rule.ActivityTestRule;
import com.sloydev.remember.R;
import com.sloydev.remember.domain.Reminder;
import com.sloydev.remember.domain.ReminderRepository;
import com.sloydev.remember.infrastructure.ServiceLocator;
import com.sloydev.remember.infrastructure.TimeMachine;
import java.util.Collections;
import kotlin.coroutines.experimental.Continuation;
import kotlin.jvm.functions.Function0;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static com.schibsted.spain.barista.BaristaAssertions.assertNotExist;
import static com.schibsted.spain.barista.BaristaClickActions.click;
import static com.schibsted.spain.barista.BaristaClickActions.clickBack;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemindersActivityTest {

  private static final LocalDateTime NOW = LocalDateTime
      .of(2017, Month.MARCH, 25, 10, 52, 42);

  private static final LocalDateTime PASSED = LocalDateTime
      .of(2016, Month.FEBRUARY, 24, 9, 51, 41);

  private static final Reminder REMINDER_WITH_DATE_TIME = new Reminder("My reminder", LocalDate.from(PASSED), LocalTime.from(PASSED));
  private static final Reminder FIRST_REMINDER = new Reminder("First reminder", LocalDate.from(PASSED), LocalTime.from(PASSED));
  private static final Reminder SECOND_REMINDER = new Reminder("Second reminder", LocalDate.from(PASSED), LocalTime.from(PASSED));

  private final ReminderRepository reminderRepository = mock(ReminderRepository.class);
  private final TimeMachine timeMachine = mock(TimeMachine.class);

  @Rule
  public ActivityTestRule<RemindersActivity> activityRule = new ActivityTestRule<>(RemindersActivity.class, true, false);

  @Before
  public void setUp() throws Exception {
    when(reminderRepository.getReminders(any(Continuation.class))).thenReturn(Collections.emptyList());
    when(timeMachine.now()).thenReturn(NOW);
    ServiceLocator.Configuration.INSTANCE.setReminderRepositoryProvider(new Function0<ReminderRepository>() {
      @Override
      public ReminderRepository invoke() {
        return reminderRepository;
      }
    });
    ServiceLocator.Configuration.INSTANCE.setTimeMachineProvider(new Function0<TimeMachine>() {
      @Override
      public TimeMachine invoke() {
        return timeMachine;
      }
    });
  }

  @Test
  public void activity_shows_fake_reminder() throws Exception {
    when(reminderRepository.getReminders(any(Continuation.class))).thenReturn(singletonList(REMINDER_WITH_DATE_TIME));

    activityRule.launchActivity(null);

    assertDisplayed("My reminder");
    assertDisplayed("2016-02-24 09:51:41");
    assertDisplayed("1 years, 1 months, 1 days");
    assertDisplayed("1 hours, 1 minutes, 1 seconds");
  }

  @Test
  public void fab_click_opens_editor_screen() throws Exception {
    activityRule.launchActivity(null);

    click(R.id.remindersAddButton);

    assertDisplayed(R.id.activity_new_reminder);
  }

  @Test
  public void reloads_list_after_adding_reminder() throws Exception {
    when(reminderRepository.getReminders(any(Continuation.class))).thenReturn(singletonList(FIRST_REMINDER));

    activityRule.launchActivity(null);

    assertDisplayed(FIRST_REMINDER.getName());
    assertNotExist(SECOND_REMINDER.getName());

    click(R.id.remindersAddButton);
    when(reminderRepository.getReminders(any(Continuation.class))).thenReturn(asList(FIRST_REMINDER, SECOND_REMINDER));
    clickBack();

    assertDisplayed(FIRST_REMINDER.getName());
    assertDisplayed(SECOND_REMINDER.getName());
  }
}