package com.imams.simpleform.ui.common

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(
    private val dd: String? = null,
    private val mm: String? = null,
    private val yyyy: String? = null,
    private val callback: (Int, Int, Int) -> Unit,
): DialogFragment(), DatePickerDialog.OnDateSetListener  {

    private val calender by lazy { Calendar.getInstance() }
    private var day: Int = calender.get(Calendar.DAY_OF_MONTH)
    private var month: Int = calender.get(Calendar.MONTH)
    private var year: Int = calender.get(Calendar.YEAR)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dd?.let { day = it.saveParseDD() }
        mm?.let { month = it.saveParseMM() }
        yyyy?.let { year = it.saveParseYYYY() }
        println("DatePickerFragment x day $day, month $month, year $year")
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        println("DatePickerFragment p1 $p1, p2 $p2, p3 $p3")
        callback.invoke(p3, p2, p1)
    }

    private fun String.saveParseDD(): Int {
        return try {
            this.toInt()
        } catch (e: Exception) {
            1
        }
    }

    private fun String.saveParseMM(): Int {
        return try {
            this.toInt().takeIf {
                it in 1..12
            } ?: 1
        } catch (e: Exception) {
            1
        }
    }

    private fun String.saveParseYYYY(): Int {
        return try {
            this.toInt().takeIf {
                it in 1990..2035
            } ?: 2010
        } catch (e: Exception) {
            2010
        }
    }
}