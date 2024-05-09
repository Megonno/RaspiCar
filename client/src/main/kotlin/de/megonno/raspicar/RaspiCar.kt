package de.megonno.raspicar

const val host = "10.21.220.46"
const val port = 5000

fun main() {
    val client = SocketClient()

    client.connect(host, port) {

    }

    while (true) {
        client.sendMessage(readln())
    }
}
