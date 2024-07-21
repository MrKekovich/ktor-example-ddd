package com.example.configuration

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

private val keyPair = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.generateKeyPair()
private val publicKey = keyPair.public as RSAPublicKey
private val privateKey = keyPair.private as RSAPrivateKey

fun Application.configureTestSecurity() {
    install(Authentication) {
        jwt {
            verifier(
                JWT
                    .require(Algorithm.RSA256(publicKey, privateKey))
                    .build()
            )

            validate { credential ->
                credential.payload.toUserPrincipal()
            }
        }
    }
}

fun generateTestToken(
    id: UUID = UUID.randomUUID(),
    username: String = "testuser",
    email: String = "test@example.com",
    roles: List<String> = listOf()
): String {
    return JWT.create()
        .withSubject(id.toString())
        .withClaim("preferred_username", username)
        .withClaim("email", email)
        .withClaim("resource_access", mapOf(
            "netrunner-client" to mapOf(
                "roles" to roles
            )
        ))
        .sign(Algorithm.RSA256(publicKey, privateKey))
}