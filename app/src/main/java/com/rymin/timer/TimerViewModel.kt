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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : BaseViewModel<TimerViewModel.Event>() {

    private var timerJob: Job? = null
    private val timeLimit = 1 * 1000L

    sealed class Event {
        class StartTimer() : Event()
        object TurnChange : Event()
        object PauseTimer : Event()
        object PlayTimer : Event()

    }

    private val _turnBlue = MutableStateFlow(false)
    val turnBlue = _turnBlue.asStateFlow()

    private val _bluetimer = MutableStateFlow(0L)
    val bluetimer = _bluetimer.asStateFlow()

    private val _redTimer = MutableStateFlow(0L)
    val redTimer = _redTimer.asStateFlow()

    private val _isStart = MutableStateFlow(false)
    val isStart = _isStart.asStateFlow()

    private val _deviceListUiState = MutableStateFlow(TimerListUiState.empty())
    val deviceListUiState: StateFlow<TimerListUiState> = _deviceListUiState.asStateFlow()


    init {
        viewModelScope.launch {
            _redTimer.value = timeLimit
            _deviceListUiState.value = deviceListUiState.value.copy(loading = false)
        }
    }

    fun startTimer() {
        _isStart.value = true
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(10)
                if (turnBlue.value) {
                    if (_bluetimer.value < 10) {
                        _bluetimer.value = 0
                        break
                    }
                    _bluetimer.value -= 10
                } else {
                    if (_redTimer.value < 10) {
                        _redTimer.value = 0
                        break
                    }
                    _redTimer.value -= 10
                }
            }
        }
    }

    fun turnChange() {
        if (_turnBlue.value) {
            _bluetimer.value = 0
            _redTimer.value = timeLimit
        } else {
            _bluetimer.value = timeLimit
            _redTimer.value = 0
        }
        _turnBlue.value = !turnBlue.value
        startTimer()
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }


    fun stopTimer() {
        _bluetimer.value = 0
        _redTimer.value = 0
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}