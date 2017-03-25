package com.sloydev.remember.domain;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReminderTest {

  private static final LocalDateTime NOW = LocalDateTime
      .of(2017, Month.MARCH, 25, 10, 52, 42);

  private static final LocalDateTime PASSED = LocalDateTime
      .of(2016, Month.FEBRUARY, 24, 9, 51, 41);

  private static final Reminder REMINDER = new Reminder("My reminder", LocalDate.from(PASSED), LocalTime.from(PASSED));

  @Test
  public void passed_date_one_unit() throws Exception {
    Reminder.DatePassed datePassed = REMINDER.getDatePassed(NOW);

    assertEquals(1, datePassed.getYears());
    assertEquals(1, datePassed.getMonths());
    assertEquals(1, datePassed.getDays());
  }

  @Test
  public void passed_time_one_unit() throws Exception {
    Reminder.TimePassed timePassed = REMINDER.getTimePassed(NOW);

    assertNotNull(timePassed);
    assertEquals(1, timePassed.getHours());
    assertEquals(1, timePassed.getMinutes());
    assertEquals(1, timePassed.getSeconds());
  }
}
