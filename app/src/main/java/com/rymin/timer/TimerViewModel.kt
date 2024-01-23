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
        object TurnChange : Event()
    }

    private val _tag = MutableStateFlow(true)
    val tag = _tag.asStateFlow()

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
                if (tag.value) {
                    _bluetimer.value--
                } else {
                    _redTimer.value--
                }
            }
        }
    }

    fun turnChange() {
        if (_tag.value) {
            _bluetimer.value = 0
            _redTimer.value = 6000
        } else {
            _bluetimer.value = 6000
            _redTimer.value = 0
        }
        _tag.value = !tag.value
        startTimer()
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