package com.example.kubik.j1edittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.*
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "myLogs"
    private var previousLength = 0
    private var currentLength = 0
    private var position = 0
    private val code = "+7 "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneInputEditText.setCompoundDrawables(PhoneCode(code, this), null, null, null)
        phoneInputEditText.compoundDrawablePadding = (resources.getDimension(R.dimen.editTextSize) * 1.3).toInt()
    }

    override fun onResume() {
        super.onResume()
        phoneInputEditText.addTextChangedListener(watcher)
        buttonShowPhone.setOnClickListener {
            textViewNumber.text = getFullPhoneNumber()
        }
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            try {
                if (s != null) {
                    currentLength = s.length
                }
                val number = removeSpaces(s)
                var result = ""
                for (i in 0 until number.length) {
                    if (result.length <= 13) {
                        if (i == 3 || i == 6 || i == 8) {
                            result += " "
                        }
                        result += number[i]
                    }
                }

                val selection =
                    if (currentLength > previousLength) {
                        if (previousLength > position) {
                            if (result[position] == ' ') {
                                position + 2
                            } else {
                                position + 1
                            }
                        } else {
                            result.length
                        }
                    } else {
                        if (position < result.length) {
                            position
                        } else {
                            result.length
                        }
                    }
                phoneInputEditText.setText(this, result, selection)
            } catch (e: Exception) {
                Log.d(TAG, e.message)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (s != null) {
                previousLength = s.length
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            position = start
        }
    }

    private fun removeSpaces(s: Editable?): String {
        var phone = ""
        s.toString().forEach {
            if (it != ' ') {
                phone += it
            }
        }
        return phone
    }

    private fun getFullPhoneNumber() = code.trim() + removeSpaces(phoneInputEditText.text)

    private fun EditText.setText(watcher: TextWatcher, text: String, selection: Int) {
        removeTextChangedListener(watcher)
        setText(text)
        setSelection(selection)
        addTextChangedListener(watcher)
    }
}