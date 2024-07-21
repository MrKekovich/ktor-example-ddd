package com.example.configuration

import com.example.exceptions.ForbiddenException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import org.apache.http.auth.AuthenticationException

fun Application.configureRouting() {
    install(Resources)

    install(StatusPages) {
//        exception<Throwable> { call, cause ->
//            call.respondText(text = "500: ${cause.message}", status = HttpStatusCode.InternalServerError)
//        }

        exception<BadRequestException> { call, cause ->
            call.respondText(text = "400: ${cause.message}", status = HttpStatusCode.BadRequest)
        }

        exception<NotFoundException> { call, cause ->
            call.respondText(text = "404: ${cause.message}", status = HttpStatusCode.NotFound)
        }

        exception<AuthenticationException> { call, cause ->
            call.respondText(text = "401: ${cause.message}", status = HttpStatusCode.Unauthorized)
        }

        exception<ForbiddenException> { call, cause ->
            call.respondText(text = "403: ${cause.message}", status = HttpStatusCode.Forbidden)
        }
    }

    routing {
        openAPI("/openapi", "openapi/documentation.yaml")
    }
}
