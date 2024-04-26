package de.megonno.raspicar

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.InetSocketAddress
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.isClosed
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.cancel
import io.ktor.utils.io.close
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

class SocketClient {
    private val clientScope = CoroutineScope(Dispatchers.IO)
    private var client: Socket? = null
    private var inputChannel: ByteReadChannel? = null
    private var outputChannel: ByteWriteChannel? = null
    private var clientJob: Job? = null

    suspend fun connectSuspend(hostname: String, port: Int, inputHandler: (String) -> Unit) {
        try {
            client = aSocket(SelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress(hostname, port))
            println("Connected with ${client!!.remoteAddress}")

            inputChannel = client!!.openReadChannel()
            outputChannel = client!!.openWriteChannel(autoFlush = true)
            println("Init input and output channel")
        } catch (exception: ConnectException) {
            println("Exception: ${exception.message}")

            client = null
            inputChannel = null
            outputChannel = null

            return
        }

        clientJob = clientScope.launch {
            try {
                while (!client!!.isClosed) {
                    val message = inputChannel!!.readUTF8Line() ?: break

                    if (message.isNotBlank()) {
                        println("Received $message")

                        inputHandler.invoke(message)
                    }
                }
            } catch (exception: Exception) {
                println("Exception: ${exception.message}")
            }
        }

        clientJob?.invokeOnCompletion {
            inputChannel?.cancel()
            outputChannel?.close()
            client?.close()
            println("Disconnected")

            inputChannel = null
            outputChannel = null
            client = null
            clientJob = null
        }
    }

    fun connect(hostname: String, port: Int, inputHandler: (String) -> Unit) {
        clientScope.launch {
            connectSuspend(hostname, port, inputHandler)
        }
    }

    suspend fun sendMessageSuspend(message: String) {
        outputChannel?.writeStringUtf8("$message\n")
        if (outputChannel != null) println("Sent: $message")
    }

    fun sendMessage(message: String) {
        clientScope.launch {
            sendMessageSuspend(message)
        }
    }

    suspend fun stopSuspend() {
        clientJob?.cancel()
        clientJob?.join()
        println("Client job canceled")
    }

    fun stop() {
        clientScope.launch {
            stopSuspend()
        }
    }
}
