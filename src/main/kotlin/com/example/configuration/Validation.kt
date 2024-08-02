package com.example.configuration

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import com.example.shared.utils.validation.Validatable
import com.example.shared.utils.validation.toValidationResult

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Validatable> { it.validate().toValidationResult() }
    }
}