package com.rymin.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rymin.timer.ui.RootScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen()
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect {
                    Timber.d("rymins it :$it")
                    when (it) {
                        is TimerViewModel.Event.StartTimer -> viewModel.startTimer()
                        TimerViewModel.Event.TurnChange -> viewModel.turnChange()
                        TimerViewModel.Event.PauseTimer -> viewModel.pauseTimer()
                        TimerViewModel.Event.PlayTimer -> viewModel.startTimer()

                        else -> {

                        }
                    }

                }

            }

        }
    }

}
