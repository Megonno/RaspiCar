package de.megonno.raspicar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.Socket

class SocketClient {
    private val clientScope = CoroutineScope(Dispatchers.IO)
    private var client: Socket? = null
    private var bufferedReader: BufferedReader? = null
    private var printWriter: PrintWriter? = null
    private var clientJob: Job? = null

    suspend fun connect(hostname: String, port: Int, inputHandler: (String) -> Unit) {
        try {
            withContext(Dispatchers.IO) {
                client = Socket(hostname, port)
                println("Connected with ${client!!.remoteSocketAddress}")

                bufferedReader = BufferedReader(InputStreamReader(client!!.getInputStream()))
                printWriter = PrintWriter(client!!.getOutputStream())
                println("Init input and output")
            }
        } catch (exception: ConnectException) {
            println("Exception: ${exception.message}")

            client = null
            bufferedReader = null
            printWriter = null

            return
        }

        clientJob = clientScope.launch {
            try {
                while (!client!!.isClosed) {
                    val message = bufferedReader!!.readLine()

                    if (message.isNotBlank()) {
                        println("Received: $message")

                        inputHandler.invoke(message)
                    }
                }
            } catch (exception: Exception) {
                println("Exception: ${exception.message}")
            }
        }

        clientJob?.invokeOnCompletion {
            bufferedReader?.close()
            printWriter?.close()
            client?.close()
            println("Disconnected")

            bufferedReader = null
            printWriter = null
            client = null
            clientJob = null
        }
    }

    fun sendMessage(message: String) {
        printWriter?.print(message)
        printWriter?.flush()
        if (printWriter != null) println("Sent: $message") else println("Output channel is null")
    }

    suspend fun stop() {
        clientJob?.cancel()
        clientJob?.join()
        println("Client job canceled")
    }
}
