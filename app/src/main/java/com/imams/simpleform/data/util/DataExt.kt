package com.imams.simpleform.data.util

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