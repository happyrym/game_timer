package com.rymin.timer.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    private val eventChannel = Channel<T>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun sendEvent(event: T) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}