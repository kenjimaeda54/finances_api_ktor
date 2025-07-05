package com.plugins

import com.api.dto.ErrorDto
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.util.Environment.jwtAudience
import com.util.Environment.jwtIssuer
import com.util.Environment.jwtRealm
import com.util.Environment.jwtSecret
import com.util.Constants
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Constants.PAYLOAD_CLAIM_PHONE
import com.util.Error
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
                val phone = credential.payload.getClaim(PAYLOAD_CLAIM_PHONE).asString()
                val uuid = credential.payload.getClaim(PAYLOAD_CLAIM_ID).asString()

                if (phone != null && credential.payload.audience.contains(jwtAudience.value) && uuid != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                val errorDto = ErrorDto(
                    httpStatusCode = HttpStatusCode.Unauthorized.value.toString(),
                    errorCode = Error.ML000.code,
                    message = Error.ML000.message
                )
                call.respond(HttpStatusCode.Unauthorized, errorDto)
            }

        }

    }

}