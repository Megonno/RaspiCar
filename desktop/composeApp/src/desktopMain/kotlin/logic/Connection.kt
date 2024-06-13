package logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Connection {
    private val connectionScope = CoroutineScope(Dispatchers.IO)
    private val client = SocketClient(connectionScope = connectionScope)

    fun connect(inputHandler: (String) -> Unit) {
        client.connect(hostname = "10.21.220.46", port = 5000, inputHandler = inputHandler)

        connectionScope.launch {
            while (true) {
                client.sendMessage("9:0")
                client.sendMessage("8:0")
                delay(250L)
            }
        }
    }

    fun sendCommand(command: Command) {
        client.sendMessage(message = command.content)
    }
}
