package de.megonno.raspicar

const val host = ""
const val port = 5000

fun main() {
    val client = SocketClient()

    client.connect(host, port) {}

    println("Commands: \n- Servo [Angle]\n- RightMotor [Speed]\n- LeftMotor [Speed]\n- CameraTilt [Angle]\n- CameraPan [Angle]\n- StartVideo\n- StopServer\n- Say [Message] (Language))\n- Debug [Message]\n\n")

    while (true) {
        val rawInput = readln()
        val input = rawInput.split(" ")

        val message = when (input.getOrNull(0)?.lowercase()) {
            "servo" -> "1:${input.getOrNull(1)?.toIntOrNull() ?: 0}"
            "rightmotor" -> "2:${input.getOrNull(1)?.toIntOrNull() ?: 0}"
            "leftmotor" -> "3:${input.getOrNull(1)?.toIntOrNull() ?: 0}"
            "cameratilt" -> "4:${input.getOrNull(1)?.toIntOrNull() ?: 0}"
            "camerapan" -> "5:${input.getOrNull(1)?.toIntOrNull() ?: 0}"
            "startvideo" -> "6:${input.getOrNull(1)?.toIntOrNull() ?: 6000}"
            "stopserver" -> "7:0"
            "say" -> "10:${input.getOrNull(1) ?: "You don't typed a valid message!"}:${input.getOrNull(2) ?: "en-US"}"
            "debug" -> rawInput.removePrefix("debug ")
            else -> null
        }

        message?.let {
            println(it)
            client.sendMessage(it)
        }
    }
}
