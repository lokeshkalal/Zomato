package com.dev.lokeshkalal.zomato.Utils

object Util {
    fun formattedTime(mins: Int) = if (mins == 60) "1 hour" else if (mins == 1) "1 minute"
    else if (mins < 59) mins.toString() + " minutes" else (mins/60).toString() + " hours"
}