package com.rymin.timer.utils

class TimeUtils {
}
const val ONE_YEAR_MILLIS: Long = 365 * 24 * 60 * 60 * 1000L
const val ONE_DAY_MILLIS: Long = 24 * 60 * 60 * 1000L
const val ONE_DAY_SEC: Long = 24 * 60 * 60
const val ONE_HOUR_MILLIS: Long = 60 * 60 * 1000L
const val ONE_MINUTE_MILLIS: Long = 60 * 1000L


fun Long.formatTime(): String {
//    val hours = this / ONE_HOUR_MILLIS
    val minutes = this / ONE_MINUTE_MILLIS
    val remainingSeconds = (this % ONE_MINUTE_MILLIS) / 1000
    val remainingMillSeconds = (this / 10) % 100
    return String.format("%02d:%02d.%02d", minutes, remainingSeconds, remainingMillSeconds)
}