package de.megonno.raspicar

const val host = "localhost"
const val port = 5000

suspend fun main() {
    val client = SocketClient()

    client.connect(host, port) {

    }

    while (true) {
        client.sendMessage(readln())
    }
}
