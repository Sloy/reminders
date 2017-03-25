package com.sloydev.remember.ui;

import android.support.test.rule.ActivityTestRule;
import com.schibsted.spain.barista.BaristaAssertions;
import com.sloydev.remember.R;
import com.sloydev.remember.domain.Reminder;
import com.sloydev.remember.domain.ReminderRepository;
import com.sloydev.remember.infrastructure.ServiceLocator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static com.schibsted.spain.barista.BaristaAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.BaristaClickActions.click;
import static com.schibsted.spain.barista.BaristaEditTextActions.writeToEditText;
import static java.util.Collections.emptyList;

public class NewReminderActivityTest {

  @Rule
  public ActivityTestRule<NewReminderActivity> activityRule = new ActivityTestRule<>(NewReminderActivity.class, true, false);

  @Before
  public void setUp() throws Exception {
    ServiceLocator.Configuration.INSTANCE.setReminderRepositoryProvider(new Function0<ReminderRepository>() {
      @Override
      public ReminderRepository invoke() {
        return new TestReminderRepository();
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

    writeToEditText(R.id.addReminderDate, "03011992");

    assertDisplayed("03/01/1992");
  }

  @Test
  public void invalid_date_shows_error() throws Exception {
    activityRule.launchActivity(null);

    writeToEditText(R.id.addReminderDate, "32132222");

    assertDisplayed("Invalid date");
  }

  @Test
  @Ignore
  public void activity_closes_after_save() throws Exception {
    activityRule.launchActivity(null);

    click(R.id.addReminderSaveButton);

    assertNotDisplayed(R.id.activity_new_reminder);
  }

  private static class TestReminderRepository implements ReminderRepository {
    @NotNull
    @Override
    public List<Reminder> getReminders() {
      return emptyList();
    }

    @Override
    public void addReminder(@NotNull Reminder reminder) {
      throw new IllegalStateException("Not implemented on this test");
    }
  }
}