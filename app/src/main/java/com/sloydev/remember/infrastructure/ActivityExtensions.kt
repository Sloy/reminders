package com.sloydev.remember.infrastructure

import android.app.Activity
import android.util.Log
import android.widget.EditText

fun Activity.log(text: String) {
    Log.d(this.javaClass.simpleName, text)
}

inline val EditText.textString: String
    get() = text.toString()


