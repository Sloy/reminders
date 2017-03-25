package com.sloydev.remember.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.sloydev.remember.R
import com.sloydev.remember.infrastructure.Try
import kotlinx.android.synthetic.main.activity_new_reminder.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


class NewReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        addReminderSaveButton.setOnClickListener { finish() }

        setupDateInputMask()
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