package ui.main

import androidx.compose.ui.graphics.Color
import logic.Command

sealed class MainAction {
    data class UpdateSpeed(val speed: Float) : MainAction()
    data class UpdateColor1(val color1: Color) : MainAction()
    data class UpdateColor2(val color2: Color) : MainAction()
    data class UpdateColor3(val color3: Color) : MainAction()
    data class UpdateUltrasonicText(val ultrasonicText: String) : MainAction()
    data class UpdateSteering(val steering: Float) : MainAction()
    data class UpdateCameraTilt(val cameraTilt: Float) : MainAction()
    data class UpdateCameraPan(val cameraPan: Float) : MainAction()
    data class SendCommand(val command: Command) : MainAction()
}
