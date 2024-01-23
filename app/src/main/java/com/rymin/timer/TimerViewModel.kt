package com.rymin.timer

import com.rymin.timer.base.BaseViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

        class StartTime(val message: String) : Event()
        class AddEvent(val message: String) : Event()
        object GotoAboutPage : Event()
    }

    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

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
                _timer.value++
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        _timer.value = 0
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}