package de.megonno.raspicar

const val host = ""
const val port = 5000

fun main() {
    val client = SocketClient()

    client.connect(host, port) {

    }

    while (true) {
        client.sendMessage(readln())
    }
}
