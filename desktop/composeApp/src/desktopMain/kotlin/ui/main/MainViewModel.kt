package ui.main

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import logic.Command
import logic.Connection

class MainViewModel {
    private val _speed = MutableStateFlow(0f)
    val speed = _speed.asStateFlow()
    private val _color1 = MutableStateFlow(Color.Unspecified)
    val color1 = _color1.asStateFlow()
    private val _color2 = MutableStateFlow(Color.Unspecified)
    val color2 = _color2.asStateFlow()
    private val _color3 = MutableStateFlow(Color.Unspecified)
    val color3 = _color3.asStateFlow()
    private val _ultrasonicText = MutableStateFlow("")
    val ultrasonicText = _ultrasonicText.asStateFlow()
    private val _steering = MutableStateFlow(0f)
    val steering = _steering.asStateFlow()
    private val _cameraTilt = MutableStateFlow(0f)
    val cameraTilt = _cameraTilt.asStateFlow()
    private val _cameraPan = MutableStateFlow(0f)
    val cameraPan = _cameraPan.asStateFlow()

    init {
        Connection.connect { message ->
            if (message.startsWith("1:")) {
                val content = message.removePrefix("1:").split(":").map { it.toInt() }.toIntArray()

                val a = content[0] / 1600f
                val b = content[1] / 1600f
                val c = content[2] / 1600f

                _color1.value = Color(a, a, a, 1f)
                _color2.value = Color(b, b, b, 1f)
                _color3.value = Color(c, c, c, 1f)
            }

            if (message.startsWith("2:")) {
                val content = message.removePrefix("2:")

                _ultrasonicText.value = content

                if (content.toFloat() < 20.0f) {
                    _speed.value = 0f
                    Connection.sendCommand(command = Command.MoveForwards(speed = 0))
                    Connection.sendCommand(command = Command.MoveBackwards(speed = 0))
                }
            }
        }
    }

    fun onAction(action: MainAction) {
        when(action) {
            is MainAction.UpdateSpeed -> _speed.value = action.speed
            is MainAction.UpdateColor1 -> _color1.value = action.color1
            is MainAction.UpdateColor2 -> _color2.value = action.color2
            is MainAction.UpdateColor3 -> _color3.value = action.color3
            is MainAction.UpdateUltrasonicText -> _ultrasonicText.value = action.ultrasonicText
            is MainAction.UpdateSteering -> _steering.value = action.steering
            is MainAction.UpdateCameraTilt -> _cameraTilt.value = action.cameraTilt
            is MainAction.UpdateCameraPan -> _cameraPan.value = action.cameraPan
            is MainAction.SendCommand -> Connection.sendCommand(command = action.command)
        }
    }
}
