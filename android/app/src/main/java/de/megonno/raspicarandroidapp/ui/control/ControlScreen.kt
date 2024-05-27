package de.megonno.raspicarandroidapp.ui.control

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ControlScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(100.dp))

            val state = rememberScrollableState { it }
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .scrollable(state = state, orientation = Orientation.Vertical)
            ) {
                Spacer(modifier = Modifier.size(100.dp))
                for (i in 0..40) {
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .width(200.dp)
                    ) {}
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Spacer(modifier = Modifier.size(100.dp))
            }

            /*var steering by remember { mutableFloatStateOf(0f) }
            Slider(
                value = steering, valueRange = -30f..30f, onValueChange = {
                    steering = it
                }, modifier = Modifier
                    .width(600.dp)
                    .rotate(90f)
            )

            var speed by remember { mutableFloatStateOf(0f) }
            Slider(
                value = speed, valueRange = -100f..100f, onValueChange = {
                    speed = it
                }, modifier = Modifier
                    .width(600.dp)
                    .rotate(90f)
            )*/
        }
    }
}

@Composable
@Preview(widthDp = 1920, heightDp = 1080)
fun Preview() {
    ControlScreen()
}
