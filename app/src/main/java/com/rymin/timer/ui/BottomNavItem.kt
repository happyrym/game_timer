package com.rymin.timer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rymin.timer.R

const val TIMER = "TIMER"
const val SETUP = "TIMELINE"
const val SETTINGS = "SETTINGS"

sealed class BottomNavItem(
    val title: Int, val icon: ImageVector, val screenRoute: String
) {
    object Timer : BottomNavItem(R.string.text_timer, Icons.Default.Home,TIMER)
    object Calendar : BottomNavItem(R.string.text_calendar, Icons.Default.CheckCircle,SETUP)
    object Setup : BottomNavItem(R.string.text_setup,Icons.Default.Settings, SETTINGS)
}