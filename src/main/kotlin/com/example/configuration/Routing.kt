package com.example.configuration

import com.example.exceptions.ForbiddenException
import com.example.post.postRoutes
import com.example.utils.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import org.apache.http.auth.AuthenticationException

fun Application.configureRouting() {
    install(Resources)

    install(StatusPages) {
        // TODO: uncomment in production:
        // exception<Throwable> { call, cause ->
        //     call.respondText(text = "500: ${cause.message}", status = HttpStatusCode.InternalServerError)
        // }

        exception<BadRequestException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest, ErrorResponse(
                    message = cause.message ?: "Bad Request",
                )
            )
        }

        exception<NotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound, ErrorResponse(
                    message = cause.message ?: "Not Found",
                )
            )
        }

        exception<AuthenticationException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized, ErrorResponse(
                    message = cause.message ?: "Unauthorized",
                )
            )
        }

        exception<ForbiddenException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden, ErrorResponse(
                    message = cause.message ?: "Forbidden",
                )
            )
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest, ErrorResponse(
                    message = cause.message ?: "Bad Request",
                )
            )
        }
    }

    routing {
        postRoutes()
        openAPI("/openapi", "openapi/documentation.yaml")
    }
}
