package com.plugins

import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.AuthScheme
import io.github.smiley4.ktoropenapi.config.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger() {

    install(OpenApi) {
        info {
            title = "Finances Service"
        }
        schemas {  }
        security {
            securityScheme("bearerAuth") {
                type = AuthType.HTTP
                scheme = AuthScheme.BEARER
                bearerFormat = "JWT"
                description = "JWT Token (e.g., Bearer <your-jwt-token>)"
            }

            defaultSecuritySchemeNames("bearerAuth")

        }
        tags {
            tagGenerator = { url ->
                when {
                    url.contains("auth") -> setOf("Auth")
                    url.contains("customers") -> setOf("Customer")
                    else -> emptySet()
                }

            }
        }
    }

}