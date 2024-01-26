package com.rymin.timer.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.rymin.timer.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rymin.timer.ui.theme.TimerTheme
import com.rymin.timer.utils.formatTime

@Composable
fun RootScreen(
    viewModel: TimerViewModel = viewModel()
) {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Scaffold(
        bottomBar = {
            NavigationBarSample(navController = navController)
        }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, viewModelStoreOwner = viewModelStoreOwner)
        }
    }
}

@Composable
fun NavigationBarSample(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(BottomNavItem.Timer, BottomNavItem.Calendar, BottomNavItem.Setup)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.icon.name) },
                label = { Text(stringResource(id = item.title)) },
                selected = selectedItem == index,
                onClick = { navController.navigate(item.screenRoute) }
            )
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController, viewModelStoreOwner: ViewModelStoreOwner) {
    NavHost(navController = navController, startDestination = BottomNavItem.Timer.screenRoute) {
        composable(
            BottomNavItem.Timer.screenRoute,
        ) {
            val model = hiltViewModel<TimerViewModel>(viewModelStoreOwner = viewModelStoreOwner)
            TimerScreen(model)
        }
        composable(BottomNavItem.Calendar.screenRoute) {
            CalendarScreen()
        }
        composable(BottomNavItem.Setup.screenRoute) {
            SetupScreen()
        }
    }
}

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = viewModel()
) {
    val blueTimerValue by viewModel.bluetimer.collectAsState()
    val redTimerValue by viewModel.redTimer.collectAsState()
    val turnBlue by viewModel.turnBlue.collectAsState()
    val showAnimate by viewModel.bluetimer.collectAsState()
    val isStart by viewModel.isStart.collectAsState()

    TimerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = blueTimerValue.formatTime(),
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .rotate(-180f),
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Row {
                    Button(
                        modifier = Modifier.height(80.dp),
                        onClick = {
                            if (isStart) {
                                viewModel.sendEvent(TimerViewModel.Event.TurnChange)
                            } else {
                                viewModel.sendEvent(TimerViewModel.Event.PlayTimer)
                            }
                        },
                    ) {
                        Text(
                            text =
                            if (isStart) {
                                "TurnChange"
                            } else {
                                "Play"
                            },
                            style = TextStyle(
                                fontSize = 36.sp
                            ),
                            modifier = Modifier
                                .rotate(if (turnBlue) -180f else 0f),
                        )
                    }
                    Button(
                        modifier = Modifier.height(80.dp),
                        onClick = {
                            viewModel.sendEvent(TimerViewModel.Event.PauseTimer)
                        },
                    ) {
                        Text(
                            text = "Pause",
                            style = TextStyle(
                                fontSize = 36.sp
                            ),
                            modifier = Modifier
                                .rotate(if (turnBlue) -180f else 0f),
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = redTimerValue.formatTime(),
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }
        }
    }
}

@Composable
fun SetupScreen(
    viewModel: TimerViewModel = viewModel()
) {
    TimerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "setup",
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .rotate(-180f),
                )
                Button(
                    modifier = Modifier.height(80.dp),
                    onClick = {
                        viewModel.sendEvent(TimerViewModel.Event.PauseTimer)
                    },
                ) {
                    Text(
                        text = "Pause",
                        style = TextStyle(
                            fontSize = 36.sp
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun (@Composable () -> Unit).Pulsating(pulseFraction: Float = 1.2f) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ), label = "sizeTransition"
    )

    Box(modifier = Modifier.scale(scale)) {
        this@Pulsating()
    }
}

@Composable
fun CalendarScreen(
    viewModel: TimerViewModel = viewModel()
) {
    TimerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "calendar",
                    style = TextStyle(
                        fontSize = 80.sp
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .rotate(-180f),
                )
                Button(
                    modifier = Modifier.height(80.dp),
                    onClick = {
                        viewModel.sendEvent(TimerViewModel.Event.PauseTimer)
                    },
                ) {
                    Text(
                        text = "Pause",
                        style = TextStyle(
                            fontSize = 36.sp
                        ),
                    )
                }
            }

        }
    }
}

@Composable
fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ), label = "sizeTransition"
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}


@Composable
fun CopyPulsating(content: @Composable () -> Unit) {

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    Box(
        modifier = Modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            rotationZ = rotation,
            translationX = offset.x,
            translationY = offset.y
        )
    ) {
        content()
    }
}