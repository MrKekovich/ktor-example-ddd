package com.example.configuration

import com.example.post.repository.PostInMemoryRepository
import com.example.post.repository.PostRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val testRepositoryModule = module {
    single<PostRepository> { PostInMemoryRepository }
}

fun Application.configureTestKoin() {
    install(Koin) {
        slf4jLogger()
        modules(testRepositoryModule, useCaseModule)
    }
}