package de.megonno.raspicar

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@Serializable
data class MainMotor(val type: MainMotorType, val speed: Int)

@Serializable
enum class MainMotorType {
    LEFT,
    RIGHT
}

const val host = "10.21.216.235"
const val port = 5000

fun main() {
    print(Json.encodeToString(MainMotor(MainMotorType.LEFT, 10)))

    runBlocking {
        val selectorManager = SelectorManager(Dispatchers.IO)
        val socket = aSocket(selectorManager).tcp().connect(host, port)

        val receiveChannel = socket.openReadChannel()
        val sendChannel = socket.openWriteChannel(autoFlush = true)

        launch(Dispatchers.IO) {
            while (true) {
                val greeting = receiveChannel.readUTF8Line()
                if (greeting != null) {
                    println(greeting)
                } else {
                    println("Server closed a connection")
                    socket.close()
                    selectorManager.close()
                    exitProcess(0)
                }
            }
        }

        launch(Dispatchers.IO) {
            while (true) {
                val myMessage = readln()
                sendChannel.writeStringUtf8("$myMessage\n")
            }
        }
    }
}
