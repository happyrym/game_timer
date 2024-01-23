package com.rymin.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rymin.timer.ui.theme.TimerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Timer("Android")
                }
            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimerTheme {
        Timer("Android")
    }
}


@Composable
fun Timer(name: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "00:00.00",
            style = TextStyle(
                fontSize = 40.sp
            ),
            modifier = Modifier
                .padding(top = 20.dp)
                .rotate(-180f),
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = "00:00.00",
            style = TextStyle(
                fontSize = 40.sp
            ),
            modifier = Modifier.padding(bottom=40.dp)
        )
    }

}