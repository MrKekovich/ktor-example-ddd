package com.example.configuration

import com.example.post.repository.PostDaoRepository
import com.example.post.repository.PostRepository
import com.example.post.usecase.PostService
import com.example.post.usecase.PostServiceImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val repositoryModule = module {
    single<PostRepository> { PostDaoRepository() }
}

val useCaseModule = module {
    single<PostService> { PostServiceImpl(get()) }
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(repositoryModule, useCaseModule)
    }
}
