package com.imams.simpleform.util

import android.text.Editable
import android.text.InputFilter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Editable?.stringOrNull(): String? {
    return this?.toString()
}

fun Editable?.longOrNull(): Long? {
    return try {
        if (this == null) return null
        this.toString().toLong()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Editable?.intOrNull(): Int? {
    return try {
        if (this == null) return null
        this.toString().toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun TextInputEditText.maxInput(length: Int) {
    this.filters = arrayOf(InputFilter.LengthFilter(length))
}

fun TextInputLayout.warnMessage(msg: String) {
    this.error = msg
}

fun TextInputLayout.errorMessage(msg: String?) {
    if (msg == null) this.error = null
    else this.error = "Kolom $msg harus diisi"
}