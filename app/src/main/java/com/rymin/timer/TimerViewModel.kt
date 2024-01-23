package com.rymin.timer

import com.rymin.timer.base.BaseViewModel

import androidx.lifecycle.viewModelScope
import com.rymin.timer.ui.TimerListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : BaseViewModel<TimerViewModel.Event>() {

    private var timerJob: Job? = null

    sealed class Event {
        object Finish : Event()
        object AppExit : Event()

        class StartTimer(val message: String) : Event()
        class StartTime(val message: String) : Event()
        object GotoAboutPage : Event()
    }

    private val _bluetimer = MutableStateFlow(0L)
    val bluetimer = _bluetimer.asStateFlow()

    private val _redTimer = MutableStateFlow(0L)
    val redTimer = _redTimer.asStateFlow()


    private val _deviceListUiState = MutableStateFlow(TimerListUiState.empty())
    val deviceListUiState: StateFlow<TimerListUiState> = _deviceListUiState.asStateFlow()


    init {
        viewModelScope.launch {
            _deviceListUiState.value = deviceListUiState.value.copy(loading = false)
        }
    }

    fun startTimer() {

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(10)
                _bluetimer.value++
                _redTimer.value++
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        _bluetimer.value = 0
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}