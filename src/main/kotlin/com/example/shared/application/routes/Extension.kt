package com.example.shared.application.routes

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.util.pipeline.PipelineContext
import java.util.*


fun PipelineContext<Unit, ApplicationCall>.getUUID(pathParameterName: String): Result<UUID?> {
    val pathParameter = call.parameters[pathParameterName]
        ?: return Result.success(null)

    val uuid: UUID = runCatching { UUID.fromString(pathParameter) }.getOrElse {
        return Result.failure(BadRequestException("Invalid $pathParameterName: '$pathParameter' Must be a valid UUID."))
    }

    return Result.success(uuid)
}

fun String.toUuidOrNull(): UUID? = runCatching { UUID.fromString(this) }.getOrNull()
