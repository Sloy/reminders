package com.sloydev.remember.infrastructure

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText

fun Activity.log(text: String) {
    Log.d(this.javaClass.simpleName, text)
}

fun Intent.startActivity(context: Context) {
    context.startActivity(this)
}

inline val EditText.textString: String
    get() = text.toString()
