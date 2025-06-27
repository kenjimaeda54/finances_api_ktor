package com.plugins

import com.api.dto.ErrorDto
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.util.Environment.jwtAudience
import com.util.Environment.jwtIssuer
import com.util.Environment.jwtRealm
import com.util.Environment.jwtSecret
import com.util.constants.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*



fun Application.configureSecurity() {

    install(Authentication) {
        jwt(Constants.NAME_AUTH_ROUTE_PROTECTED) {
            realm = jwtRealm.value
            verifier(
                JWT
                    .require(
                        Algorithm.HMAC256(
                            jwtSecret.value
                        )
                    )
                    .withAudience(
                        jwtAudience.value
                    )
                    .withIssuer(
                        jwtIssuer.value
                    )
                    .build()
            )
            validate { credential ->
                val phone = credential.payload.getClaim("customerPhone").asString()

                if (phone != null && credential.payload.audience.contains(jwtAudience.value)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                val errorDto = ErrorDto(
                    httpStatusCode = HttpStatusCode.Unauthorized.value.toString(),
                    errorCode = "Unauthorized",
                    message = "Token is not valid or expired"
                )
                call.respond(HttpStatusCode.Unauthorized, errorDto)
            }

        }

    }

}