package com.rymin.timer.utils

class TimeUtils {
}


fun Long.formatTime(): String {
//    val hours = this / 360000
    val minutes = this / 6000
    val remainingSeconds = (this % 6000) / 100
    val remainingMillSeconds = this % 100
    return String.format("%02d:%02d.%02d", minutes, remainingSeconds, remainingMillSeconds)
}