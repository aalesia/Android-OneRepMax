package com.aalesia.onerepmax.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(format: String = "MMM dd yyyy"): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(this)
}