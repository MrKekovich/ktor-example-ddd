package com.example.post.application.route

import com.example.configuration.UserPrincipal
import com.example.post.application.dto.PostRq
import com.example.post.domain.usecase.PostService
import com.example.shared.application.routes.getUUID
import com.example.shared.application.routes.toUuidOrNull
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.postRoutes() {
    val postService: PostService by inject()

    authenticate {
        route("/posts") {
            get {
                val userId = call.parameters["userId"]?.toUuidOrNull()

                val response = if (userId != null) {
                    postService.findByUser(userId)
                } else {
                    postService.findAll()
                }

                call.respond(response)
            }

            post {
                val user = call.principal<UserPrincipal>()!!

                val rq = call.receive<PostRq>()

                val response = postService.save(rq, user.id)

                call.respond(response)
            }

            route("/{id}") {
                get {
                    val id = getUUID("id").getOrThrow()!!

                    val response = postService.findById(id)

                    call.respond(response)
                }

                put {
                    val user = call.principal<UserPrincipal>()!!

                    val id = getUUID("id").getOrThrow()!!
                    val rq = call.receive<PostRq>()

                    val response = postService.update(id = id, postRq = rq, userId = user.id)

                    call.respond(response)
                }

                delete {
                    val user = call.principal<UserPrincipal>()!!

                    val id = getUUID("id").getOrThrow()!!

                    val response = postService.delete(id = id, userId = user.id)

                    call.respond(response)
                }
            }
        }
    }
}
