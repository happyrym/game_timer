package com.rymin.timer.ui

data class TimerListUiState(
    val loading: Boolean,
    val redTurn: Boolean
) {
    companion object {
        fun empty(): TimerListUiState = TimerListUiState(true,true)
    }
}