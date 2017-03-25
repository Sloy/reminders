package com.sloydev.remember.data

data class ReminderFirebaseModel(var name: String?, var date: String?, var time: String?) {
    constructor() : this(null, null, null)
}