package logic

sealed class Command(val content: String) {
    data class ChangeSteering(val steering: Int) : Command(content = "1:$steering")
    data class MoveForwards(val speed: Int) : Command(content = "2:$speed")
    data class MoveBackwards(val speed: Int) : Command(content = "3:${speed * -1}")
    data class ChangeCameraTilt(val cameraTilt: Int) : Command(content = "4:$cameraTilt")
    data class ChangeCameraPan(val cameraPan: Int) : Command(content = "5:$cameraPan")
    data object StartCamera : Command(content = "6:0")
}
