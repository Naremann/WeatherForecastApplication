package com.example.weatherforecastapplication

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(date)
    }
}