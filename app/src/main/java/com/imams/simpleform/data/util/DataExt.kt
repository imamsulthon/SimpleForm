package com.imams.simpleform.data.util

import android.content.Context
import com.imams.simpleform.data.model.Province
import java.io.IOException
import java.lang.Double.parseDouble
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataExt {
    fun String.checkValidDate(): Boolean {
        val x = this.replace("/", "").trim()
        println("CheckValidDate after $x")
        if (x.length != 8) return false
        return try {
            val dd = x.substring(0, 2).toLong()
            val mm = x.substring(2, 4).toLong()
            val yy = x.substring(4, 8).toLong()
            println("CheckValidDate $dd $mm $yy")
            dd <= 31 && mm <= 12 && yy in 1990 .. 2023
        } catch (e: Exception) { e.printStackTrace()
            println("CheckValidDate ${e.message}")
            false
        }
    }

    fun String.checkValidIdCardNumber(): Boolean {
        return if (this.length != 16) false
        else {
            var numeric = true
            try { parseDouble(this)
            } catch (e: NumberFormatException) { numeric = false }
            numeric
        }
    }

    fun creatingDate(dd: Int, mm: Int, yyyy: Int): String {
        return try {
            val date = LocalDate.of(yyyy, mm + 1, dd)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            date.format(formatter)
        } catch (e: DateTimeException) {
            e.printStackTrace()
            ""
        }
    }

    fun String.asDD(): Int? {
        return try {
            val format = this.replace("/", "")
            return format.substring(0, 2).toInt()
        } catch (e: Exception) {
            return null
        }
    }

    fun String.asMM(): Int? {
        return try {
            val format = this.replace("/", "")
            return format.substring(2, 4).toInt()
        } catch (e: Exception) {
            return null
        }
    }

    fun String.asYYYY(): Int? {
        return try {
            val format = this.replace("/", "")
            return format.substring(4, 8).toInt()
        } catch (e: Exception) {
            return null
        }
    }

    fun getJSONFile(context: Context, fileName: String): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }

}