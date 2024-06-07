import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logic.SocketClient

@Composable
fun App() {
    val client = SocketClient()

    val reciveScope = CoroutineScope(Dispatchers.IO)

    var speed by remember { mutableStateOf(0f) }

    var color1 by remember { mutableStateOf(Color.Unspecified) }
    var color2 by remember { mutableStateOf(Color.Unspecified) }
    var color3 by remember { mutableStateOf(Color.Unspecified) }

    var ultrasonicText by remember { mutableStateOf("") }

    client.connect(hostname = "10.21.220.46", port = 5000) { message ->
        if (message.startsWith("1:")) {
            val content = message.removePrefix("1:").split(":").map { it.toInt() }.toIntArray()

            val a = content[0] / 1600f
            val b = content[1] / 1600f
            val c = content[2] / 1600f

            color1 = Color(a, a, a, 1f)
            color2 = Color(b, b, b, 1f)
            color3 = Color(c, c, c, 1f)
        }

        if (message.startsWith("2:")) {
            val content = message.removePrefix("2:")

            ultrasonicText = content

            if (content.toFloat() < 20.0f) {
                speed = 0f
                client.sendMessage("2:0")
                client.sendMessage("3:0")
            }
        }
    }

    reciveScope.launch {
        while (true) {
            client.sendMessage("9:0")
            client.sendMessage("8:0")
            delay(250L)
        }
    }

    MaterialTheme {
        Row {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.66f)
            ) {
                Row {
                    Card(
                        backgroundColor = color1,
                        modifier = Modifier.size(100.dp)
                    ) {}
                    Card(
                        backgroundColor = color2,
                        modifier = Modifier.size(100.dp)
                    ) {}
                    Card(
                        backgroundColor = color3,
                        modifier = Modifier.size(100.dp)
                    ) {}
                }
                Spacer(modifier = Modifier.size(200.dp))
                Text(text = "Distance: $ultrasonicText", fontSize = 36.sp)
                Button(onClick = {
                    client.sendMessage("6:0")
                }) {
                    Text(text = "Camera")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    Text(text = "speed      ", fontSize = 22.sp)
                    Slider(
                        value = speed,
                        onValueChange = {
                            speed = it
                            if (speed > 0) {
                                client.sendMessage("2:${speed.toInt()}")
                            } else {
                                client.sendMessage("3:${speed.toInt() * -1}")
                            }
                        },
                        valueRange = -100f..100f,
                        modifier =
                        Modifier
                            .graphicsLayer {
                                rotationZ = 270f
                                transformOrigin = TransformOrigin(0f, 0f)
                            }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(
                                    Constraints(
                                        minWidth = constraints.minHeight,
                                        maxWidth = constraints.maxHeight,
                                        minHeight = constraints.minWidth,
                                        maxHeight = constraints.maxHeight,
                                    )
                                )
                                layout(placeable.height, placeable.width) {
                                    placeable.place(-placeable.width, 0)
                                }
                            }
                            .width(720.dp)
                            .height(50.dp)
                    )

                    Button(onClick = {
                        speed = 0f
                        client.sendMessage("2:0")
                        client.sendMessage("3:0")
                    }) {
                        Text(text = "Reset")
                    }
                }

                var steering by remember { mutableStateOf(0f) }
                Column {
                    Text(text = "steering   ", fontSize = 22.sp)
                    Slider(
                        value = steering,
                        onValueChange = {
                            steering = it
                            client.sendMessage("1:${steering.toInt()}")
                        },
                        valueRange = -30f..30f,
                        modifier = Modifier.graphicsLayer {
                            rotationZ = 270f
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(
                                    Constraints(
                                        minWidth = constraints.minHeight,
                                        maxWidth = constraints.maxHeight,
                                        minHeight = constraints.minWidth,
                                        maxHeight = constraints.maxHeight,
                                    )
                                )
                                layout(placeable.height, placeable.width) {
                                    placeable.place(-placeable.width, 0)
                                }
                            }
                            .width(720.dp)
                            .height(50.dp)
                    )

                    Button(onClick = {
                        steering = 0f
                        client.sendMessage("1:0")
                    }) {
                        Text(text = "Reset")
                    }
                }

                var cameraTilt by remember { mutableStateOf(0f) }
                Column {
                    Text(text = "cameraTilt", fontSize = 22.sp)
                    Slider(
                        value = cameraTilt,
                        onValueChange = {
                            cameraTilt = it
                            client.sendMessage("4:${cameraTilt.toInt()}")
                        },
                        valueRange = -35f..65f,
                        modifier = Modifier.graphicsLayer {
                            rotationZ = 270f
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(
                                    Constraints(
                                        minWidth = constraints.minHeight,
                                        maxWidth = constraints.maxHeight,
                                        minHeight = constraints.minWidth,
                                        maxHeight = constraints.maxHeight,
                                    )
                                )
                                layout(placeable.height, placeable.width) {
                                    placeable.place(-placeable.width, 0)
                                }
                            }
                            .width(720.dp)
                            .height(50.dp)
                    )
                }

                var cameraPan by remember { mutableStateOf(0f) }
                Column {
                    Text(text = "cameraPan  ", fontSize = 22.sp)
                    Slider(
                        value = cameraPan,
                        onValueChange = {
                            cameraPan = it
                            client.sendMessage("5:${cameraPan.toInt()}")
                        },
                        valueRange = -58f..58f,
                        modifier = Modifier.graphicsLayer {
                            rotationZ = 270f
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(
                                    Constraints(
                                        minWidth = constraints.minHeight,
                                        maxWidth = constraints.maxHeight,
                                        minHeight = constraints.minWidth,
                                        maxHeight = constraints.maxHeight,
                                    )
                                )
                                layout(placeable.height, placeable.width) {
                                    placeable.place(-placeable.width, 0)
                                }
                            }
                            .width(720.dp)
                            .height(50.dp)
                    )
                }
            }
        }
    }
}
