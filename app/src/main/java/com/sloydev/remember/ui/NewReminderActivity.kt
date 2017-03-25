package com.sloydev.remember.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.sloydev.remember.R
import com.sloydev.remember.domain.Reminder
import com.sloydev.remember.domain.ReminderRepository
import com.sloydev.remember.infrastructure.ServiceLocator
import com.sloydev.remember.infrastructure.Try
import com.sloydev.remember.infrastructure.textString
import kotlinx.android.synthetic.main.activity_new_reminder.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


class NewReminderActivity : AppCompatActivity() {

    companion object Factory {
        fun createIntent(context: Context): Intent {
            return Intent(context, NewReminderActivity::class.java)
        }
    }

    lateinit var reminderRepository: ReminderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        reminderRepository = ServiceLocator.remindersRepository()

        addReminderSaveButton.setOnClickListener { createReminder() }
        addReminderName.addTextChangedListener(TextWatcherAdapter {
            addReminderNameLayout.isErrorEnabled = false
        })

        setupDateInputMask()
    }

    private fun createReminder() {
        val name = addReminderName.textString
        val parseDate = parseDate(addReminderDate.textString)

        when {
            name.isBlank() -> addReminderNameLayout.error = "Mandatory"
            parseDate.isFailure() -> return
            else -> {
                val reminder = Reminder(
                        name = name,
                        date = parseDate.get()
                )
                reminderRepository.addReminder(reminder)
                finish()
            }
        }
    }

    private fun setupDateInputMask() {
        val dateMaskListener = MaskedTextChangedListener(
                format = "[00]/[00]/[0000]",
                autocomplete = true,
                field = addReminderDate,
                listener = null,
                valueListener = object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        if (!maskFilled) {
                            addReminderDateLayout.isErrorEnabled = false
                            return
                        }

                        val formattedDate = addReminderDate.text.toString()
                        parseDate(formattedDate)
                                .onSuccess {
                                    addReminderDateLayout.isErrorEnabled = false
                                }
                                .onFailure {
                                    addReminderDateLayout.isErrorEnabled = true
                                    addReminderDateLayout.error = "Invalid date"
                                }
                    }

                }
        )

        addReminderDate.apply {
            addTextChangedListener(dateMaskListener)
            onFocusChangeListener = dateMaskListener
        }
    }

    private fun parseDate(dateText: String): Try<LocalDate> {
        return Try {
            LocalDate.parse(dateText, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

}