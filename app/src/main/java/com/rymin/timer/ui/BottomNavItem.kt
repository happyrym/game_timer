package com.rymin.timer.ui

import com.rymin.timer.R

const val TIMER = "TIMER"
const val SETUP = "TIMELINE"
const val SETTINGS = "SETTINGS"

sealed class BottomNavItem(
    val title: Int, val screenRoute: String
) {
    object Timer : BottomNavItem(R.string.text_timer, TIMER)
    object Calendar : BottomNavItem(R.string.text_calendar, SETUP)
    object Setup : BottomNavItem(R.string.text_setup, SETTINGS)
}