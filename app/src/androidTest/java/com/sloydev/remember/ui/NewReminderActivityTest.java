package com.sloydev.remember.ui;

import android.support.test.rule.ActivityTestRule;
import com.sloydev.remember.R;
import com.sloydev.remember.domain.Reminder;
import com.sloydev.remember.domain.ReminderRepository;
import com.sloydev.remember.infrastructure.ServiceLocator;
import kotlin.jvm.functions.Function0;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static com.schibsted.spain.barista.BaristaAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.BaristaClickActions.click;
import static com.schibsted.spain.barista.BaristaEditTextActions.writeToEditText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NewReminderActivityTest {

  private static final String REMINDER_NAME = "My Event";
  private static final String INVALID_DATE_INPUT = "32132222";
  private static final String VALID_DATE_INPUT = "03011992";
  private static final String VALID_DATE_OUTPUT = "03/01/1992";
  private static final LocalDate VALID_DATE_PARSED = LocalDate.of(1992, Month.JANUARY, 3);

  @Rule
  public ActivityTestRule<NewReminderActivity> activityRule = new ActivityTestRule<>(NewReminderActivity.class, true, false);

  private ReminderRepository reminderRepository = mock(ReminderRepository.class);

  @Before
  public void setUp() throws Exception {
    ServiceLocator.Configuration.INSTANCE.setReminderRepositoryProvider(new Function0<ReminderRepository>() {
      @Override
      public ReminderRepository invoke() {
        return reminderRepository;
      }
    });
  }

  @Test
  public void activity_opens() throws Exception {
    activityRule.launchActivity(null);

    assertDisplayed(R.id.activity_new_reminder);
  }

  @Test
  public void write_date_produces_formatted_date() throws Exception {
    activityRule.launchActivity(null);

    writeToEditText(R.id.addReminderDate, VALID_DATE_INPUT);

    assertDisplayed(VALID_DATE_OUTPUT);
  }

  @Test
  public void invalid_date_shows_error() throws Exception {
    activityRule.launchActivity(null);

    writeToEditText(R.id.addReminderDate, INVALID_DATE_INPUT);

    assertDisplayed("Invalid date");
  }

  @Test
  public void event_is_saved_with_input_data() throws Exception {
    activityRule.launchActivity(null);

    writeToEditText(R.id.addReminderName, REMINDER_NAME);
    writeToEditText(R.id.addReminderDate, VALID_DATE_INPUT);
    click(R.id.addReminderSaveButton);

    Reminder expectedReminder = new Reminder(REMINDER_NAME, VALID_DATE_PARSED, null);
    verify(reminderRepository).addReminder(expectedReminder);
  }

  @Test
  @Ignore
  public void activity_closes_after_save() throws Exception {
    activityRule.launchActivity(null);

    click(R.id.addReminderSaveButton);

    assertNotDisplayed(R.id.activity_new_reminder);
  }
}