package com.sloydev.bariksta

import android.support.annotation.IdRes
import com.schibsted.spain.barista.BaristaEditTextActions.writeToEditText

fun write(text: String) = WriteAction(text)
class WriteAction(val text: String) {
    infix fun into(@IdRes id: Int) = writeToEditText(id, text)
}
