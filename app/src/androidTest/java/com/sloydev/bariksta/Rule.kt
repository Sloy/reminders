package com.sloydev.bariksta

import android.app.Activity
import android.support.test.rule.ActivityTestRule

class BarikstaActivityTestRule<T : Activity>(clazz: Class<T>) : android.support.test.rule.ActivityTestRule<T>(clazz, true, false) {

    fun launch() {
        launchActivity(null)
    }
}

inline fun <reified T : Activity> activityRule(): BarikstaActivityTestRule<T> {
    return BarikstaActivityTestRule(T::class.java)
}
