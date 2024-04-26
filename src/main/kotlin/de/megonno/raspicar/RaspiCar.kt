package de.megonno.raspicar

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class MainMotor(val type: MainMotorType, val speed: Int)

@Serializable
enum class MainMotorType {
    LEFT,
    RIGHT
}

const val host = "10.21.216.235"
const val port = 5000

val client = SocketClient()

fun main() {
    print(Json.encodeToString(MainMotor(MainMotorType.LEFT, 10)))

    client.connect(host, port) {  }
}
