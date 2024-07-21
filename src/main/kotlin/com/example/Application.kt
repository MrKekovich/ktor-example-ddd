package com.example

import com.example.configuration.configureDatabase
import com.example.configuration.configureHTTP
import com.example.configuration.configureKoin
import com.example.configuration.configureMonitoring
import com.example.configuration.configureRouting
import com.example.configuration.configureSecurity
import com.example.configuration.configureSerialization
import com.example.configuration.configureValidation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureDatabase()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
    configureValidation()
}
