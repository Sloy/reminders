package com.sloydev.remember;

import android.support.test.rule.ActivityTestRule;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;
import static java.util.Collections.singletonList;

public class RemindersActivityTest {

  private static final LocalDateTime NOW = LocalDateTime
      .of(2017, Month.MARCH, 25, 10, 52, 42);

  private static final LocalDateTime PASSED = LocalDateTime
      .of(2016, Month.FEBRUARY, 24, 9, 51, 41);

  private static final TemporalEvent EVENT_WITH_DATE_TIME = new TemporalEvent("My event", LocalDate.from(PASSED), LocalTime.from(PASSED));

  @Rule
  public ActivityTestRule<RemindersActivity> activityRule = new ActivityTestRule<>(RemindersActivity.class, true, false);

  @Before
  public void setUp() throws Exception {
    ServiceLocator.Configuration.INSTANCE.setRemindersRepositoryProvider(new Function0<RemindersRepository>() {
      @Override
      public RemindersRepository invoke() {
        return new TestRemindersRepository();
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
  public void activity_shows_fake_event() throws Exception {
    activityRule.launchActivity(null);

    assertDisplayed("My event");
    assertDisplayed("2016-02-24 09:51:41");
    assertDisplayed("1 years, 1 months, 1 days");
    assertDisplayed("1 hours, 1 minutes, 1 seconds");
  }

  @Test
  @Ignore
  public void activity_shows_birthday() throws Exception {
    activityRule.launchActivity(null);

    assertDisplayed("Birthday");
    assertDisplayed("1992-01-03");
    assertDisplayed("25 years, 2 months, 22 days");
  }

  @Test
  @Ignore
  public void activity_shows_barcelona() throws Exception {
    activityRule.launchActivity(null);

    assertDisplayed("Barcelona");
    assertDisplayed("2014-06-20 10:10");
    assertDisplayed("2 years, 9 months, 5 days");
  }

  private static class TestRemindersRepository implements RemindersRepository {
    @NotNull
    @Override
    public List<TemporalEvent> getEvents() {
      return singletonList(EVENT_WITH_DATE_TIME);
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