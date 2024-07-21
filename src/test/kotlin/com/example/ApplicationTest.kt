package com.example

import com.example.configuration.configureHTTP
import com.example.configuration.configureMonitoring
import com.example.configuration.configureRouting
import com.example.configuration.configureSerialization
import com.example.configuration.configureTestKoin
import com.example.configuration.configureTestSecurity
import com.example.configuration.configureValidation
import io.ktor.server.application.Application

fun Application.testModule() {
    configureTestKoin()
    configureTestSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
    configureValidation()
}
