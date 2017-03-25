package com.sloydev.remember.ui;

import android.support.test.rule.ActivityTestRule;
import com.sloydev.remember.R;
import com.sloydev.remember.domain.Reminder;
import com.sloydev.remember.domain.ReminderRepository;
import com.sloydev.remember.infrastructure.ServiceLocator;
import com.sloydev.remember.infrastructure.TimeMachine;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static com.schibsted.spain.barista.BaristaClickActions.click;
import static java.util.Collections.singletonList;

public class RemindersActivityTest {

  private static final LocalDateTime NOW = LocalDateTime
      .of(2017, Month.MARCH, 25, 10, 52, 42);

  private static final LocalDateTime PASSED = LocalDateTime
      .of(2016, Month.FEBRUARY, 24, 9, 51, 41);

  private static final Reminder REMINDER_WITH_DATE_TIME = new Reminder("My reminder", LocalDate.from(PASSED), LocalTime.from(PASSED));

  @Rule
  public ActivityTestRule<RemindersActivity> activityRule = new ActivityTestRule<>(RemindersActivity.class, true, false);

  @Before
  public void setUp() throws Exception {
    ServiceLocator.Configuration.INSTANCE.setReminderRepositoryProvider(new Function0<ReminderRepository>() {
      @Override
      public ReminderRepository invoke() {
        return new TestReminderRepository();
      }
    });
    ServiceLocator.Configuration.INSTANCE.setTimeMachineProvider(new Function0<TimeMachine>() {
      @Override
      public TimeMachine invoke() {
        return new TestTimeMachine();
      }
    });
  }

  @Test
  public void activity_shows_fake_reminder() throws Exception {
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

  private static class TestReminderRepository implements ReminderRepository {
    @NotNull
    @Override
    public List<Reminder> getReminders() {
      return singletonList(REMINDER_WITH_DATE_TIME);
    }

    @Override
    public void addReminder(@NotNull Reminder reminder) {
      throw new IllegalStateException("Not implemented on this test");
    }
  }

  private static class TestTimeMachine implements TimeMachine {
    @NotNull
    @Override
    public LocalDateTime now() {
      return NOW;
    }
  }
}