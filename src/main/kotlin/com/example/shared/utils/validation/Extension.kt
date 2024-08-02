package com.example.shared.utils.validation

import Violation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun List<Violation>.toValidationResult(): ValidationResult = when {
    isEmpty() -> ValidationResult.Valid
    else -> ValidationResult.Invalid(map { it.message })
}
