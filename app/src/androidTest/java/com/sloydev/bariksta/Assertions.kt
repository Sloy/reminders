package com.sloydev.bariksta

import android.support.annotation.StringRes
import com.schibsted.spain.barista.BaristaAssertions

val assert = Assertion()

class Assertion {

    infix fun displayed(text: String) {
        BaristaAssertions.assertDisplayed(text)
    }

    infix fun displayed(@StringRes textRes: Int) {
        BaristaAssertions.assertDisplayed(textRes)
    }

    infix fun notExist(@StringRes textRes: Int) {
        BaristaAssertions.assertNotExist(textRes)
    }

    infix fun notExist(text: String) {
        BaristaAssertions.assertNotExist(text)
    }
}

