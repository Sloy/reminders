package com.sloydev.remember.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.sloydev.remember.R
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.infrastructure.ServiceLocator
import com.sloydev.remember.infrastructure.TimeMachine
import com.sloydev.remember.infrastructure.startActivity
import kotlinx.android.synthetic.main.activity_reminders.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class RemindersActivity : AppCompatActivity() {

    lateinit var reminderRepository: ReminderRepository
    lateinit var timeMachine: TimeMachine
    lateinit var adapter: RemindersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        reminderRepository = ServiceLocator.remindersRepository()
        timeMachine = ServiceLocator.timeMachine()

        adapter = RemindersAdapter(timeMachine)
        remindersList.adapter = adapter
        remindersList.layoutManager = LinearLayoutManager(this)
        remindersAddButton.setOnClickListener {
            NewReminderActivity.createIntent(this)
                    .startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        loadReminders()
    }

    private fun loadReminders() {
        launch(UI) {
            val reminders = reminderRepository.getReminders()
            adapter.updateReminders(reminders)
        }
    }

}
