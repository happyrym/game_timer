package com.rymin.timer.utils

class TimeUtils {
}


fun Long.formatTime(): String {
//    val hours = this / 360000
    val minutes = this / 3600
    val remainingSeconds = (this % 3600) / 60
    val remainingMillSeconds = this % 60
    return String.format("%02d:%02d.%02d", minutes, remainingSeconds, remainingMillSeconds)
}