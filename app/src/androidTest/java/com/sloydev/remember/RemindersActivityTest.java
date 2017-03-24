package com.sloydev.remember;

import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static com.schibsted.spain.barista.BaristaAssertions.assertDisplayed;

public class RemindersActivityTest {

  @Rule
  public ActivityTestRule<RemindersActivity> activityRule = new ActivityTestRule<RemindersActivity>(RemindersActivity.class, true, false);

  @Test
  public void activity_shows_birthday() throws Exception {
    activityRule.launchActivity(null);

    assertDisplayed("Birthday");
    assertDisplayed("1992-01-03");
  }
}