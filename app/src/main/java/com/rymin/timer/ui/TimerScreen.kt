package com.rymin.timer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rymin.timer.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rymin.timer.ui.theme.TimerTheme
import com.rymin.timer.utils.formatTime


@Composable
fun TimerScreen(
    viewModel: TimerViewModel = viewModel()
) {
    val timerValue by viewModel.timer.collectAsState()

    TimerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = timerValue.formatTime(),
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .rotate(-180f),
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    modifier = Modifier.height(48.dp),
                    onClick = {
                        viewModel.startTimer()
                    },
                ) {
                    Text(text = "Button")
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = timerValue.formatTime(),
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }
        }
    }

}