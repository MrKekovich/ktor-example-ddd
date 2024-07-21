package com.example.configuration

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val repositoryModule = module {
}

val serviceModule = module {
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(repositoryModule, serviceModule)
    }
}
