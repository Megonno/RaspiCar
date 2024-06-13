package ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.Command

@Composable
fun AppRoot(viewModel: MainViewModel) {
    App(
        speed = viewModel.speed.collectAsState().value,
        color1 = viewModel.color1.collectAsState().value,
        color2 = viewModel.color2.collectAsState().value,
        color3 = viewModel.color3.collectAsState().value,
        ultrasonicText = viewModel.ultrasonicText.collectAsState().value,
        steering = viewModel.steering.collectAsState().value,
        cameraTilt = viewModel.cameraTilt.collectAsState().value,
        cameraPan = viewModel.cameraPan.collectAsState().value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun App(
    speed: Float,
    color1: Color,
    color2: Color,
    color3: Color,
    ultrasonicText: String,
    steering: Float,
    cameraTilt: Float,
    cameraPan: Float,
    onAction: (MainAction) -> Unit,
) {
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
                    onAction.invoke(MainAction.SendCommand(command = Command.StartCamera))
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
                            onAction.invoke(MainAction.UpdateSpeed(speed = it))
                            if (speed > 0) {
                                onAction.invoke(MainAction.SendCommand(command = Command.MoveForwards(speed = speed.toInt())))
                            } else {
                                onAction.invoke(MainAction.SendCommand(command = Command.MoveBackwards(speed = speed.toInt())))
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
                        onAction.invoke(MainAction.UpdateSpeed(speed = 0f))
                        onAction.invoke(MainAction.SendCommand(command = Command.MoveForwards(speed = 0)))
                        onAction.invoke(MainAction.SendCommand(command = Command.MoveBackwards(speed = 0)))
                    }) {
                        Text(text = "Reset")
                    }
                }

                Column {
                    Text(text = "steering   ", fontSize = 22.sp)
                    Slider(
                        value = steering,
                        onValueChange = {
                            onAction.invoke(MainAction.UpdateSteering(steering = it))
                            onAction.invoke(MainAction.SendCommand(command = Command.ChangeSteering(steering = steering.toInt())))
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
                        onAction.invoke(MainAction.UpdateSteering(steering = 0f))
                        onAction.invoke(MainAction.SendCommand(command = Command.ChangeSteering(steering = 0)))
                    }) {
                        Text(text = "Reset")
                    }
                }

                Column {
                    Text(text = "cameraTilt", fontSize = 22.sp)
                    Slider(
                        value = cameraTilt,
                        onValueChange = {
                            onAction.invoke(MainAction.UpdateCameraTilt(cameraTilt = it))
                            onAction.invoke(MainAction.SendCommand(command = Command.ChangeCameraTilt(cameraTilt = cameraTilt.toInt())))
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

                Column {
                    Text(text = "cameraPan  ", fontSize = 22.sp)
                    Slider(
                        value = cameraPan,
                        onValueChange = {
                            onAction.invoke(MainAction.UpdateCameraPan(cameraPan = it))
                            onAction.invoke(MainAction.SendCommand(command = Command.ChangeCameraPan(cameraPan = cameraPan.toInt())))
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
