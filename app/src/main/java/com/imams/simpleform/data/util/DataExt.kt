package com.imams.simpleform.data.util

import java.lang.Double.parseDouble
import java.lang.Long.parseLong

object DataExt {
    fun String.checkValidDate(): Boolean {
        if (this.length != 8) return false
        return try {
            val dd = this.substring(0, 2).toLong()
            val mm = this.substring(2, 4).toLong()
            val yy = this.substring(4, 8).toLong()
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
}