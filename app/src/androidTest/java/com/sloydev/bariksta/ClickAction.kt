package com.sloydev.bariksta

import android.support.annotation.IdRes
import com.schibsted.spain.barista.BaristaClickActions

val click = ClickAction()

class ClickAction {

    infix fun on(@IdRes id: Int) {
        BaristaClickActions.click(id)
    }
}