package com.example.configuration

import com.auth0.jwk.Jwk
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.auth0.jwt.interfaces.Payload
import com.example.exceptions.ForbiddenException
import com.example.utils.Environment
import com.example.utils.toUuidOrNull
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.jwt
import org.apache.http.auth.AuthenticationException
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.concurrent.TimeUnit

private val kcHost = Environment["KEYCLOAK_HOST"]
private val kcPort = Environment["KEYCLOAK_PORT"]
private val kcRealm = Environment["KEYCLOAK_REALM"]

private val keycloakUrl = URL("http://$kcHost:$kcPort/realms/$kcRealm/protocol/openid-connect/certs")

private val provider = JwkProviderBuilder(keycloakUrl)
    .cached(10, 24, TimeUnit.HOURS)
    .build()

fun Application.configureSecurity() {
    install(Authentication) {
        jwt {
            verifier(::verify)

            validate {
                runCatching { it.payload.toUserPrincipal() }.getOrNull()
            }
        }
    }
}

data class UserPrincipal(
    val id: UUID,
    val username: String,
    val email: String?,
    val roles: List<String>,
) : Principal {
    fun requireRoles(vararg requiredRoles: String) {
        if (!roles.containsAll(requiredRoles.toSet())) {
            throw ForbiddenException("You don't have access to this resource")
        }
    }

    fun hasRoles(vararg requiredRoles: String): Boolean = roles.containsAll(requiredRoles.toSet())
}

private fun verify(header: HttpAuthHeader): JWTVerifier? {
    val rawToken = header.toString()
    if (!rawToken.contains("Bearer ")) {
        throw AuthenticationException("The token must be of the 'Bearer' type")
    }
    val token = rawToken.replace("Bearer ", "")

    val jwt: DecodedJWT = JWT.decode(token)
    val jwk: Jwk = provider.get(jwt.keyId)

    val publicKey = jwk.publicKey as RSAPublicKey

    return JWT
        .require(Algorithm.RSA256(publicKey, null))
        .build()
}

@Suppress("UNCHECKED_CAST")
private fun Payload.toUserPrincipal(): UserPrincipal {
    val id = getClaim("sub").asString().toUuidOrNull()
        ?: throw IllegalArgumentException("Subject (id) is missing or is invalid UUID")
    val username: String = getClaim("preferred_username").asString()
    val email: String = getClaim("email").asString()

    val resourceAccess = getClaim("resource_access").asMap()
    val netrunnerClient = resourceAccess["netrunner-client"] as? Map<String, Any>
    val roles: List<String> = netrunnerClient?.get("roles") as? List<String> ?: emptyList()

    return UserPrincipal(id, username, email, roles)
}
