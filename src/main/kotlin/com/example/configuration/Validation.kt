package com.example.configuration

import com.example.shared.dto.Validatable
import com.example.utils.toValidationResult
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Validatable> { it.validate().toValidationResult() }
    }
}