package com.plugins

import com.api.routes.customerAuth
import com.api.routes.customerRouting
import com.util.constants.Constants
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*



fun Application.configureRoutes() {

    routing {
        customerAuth()

        authenticate(Constants.nameAuthRouteProteced) {
            customerRouting()
        }
    }
}