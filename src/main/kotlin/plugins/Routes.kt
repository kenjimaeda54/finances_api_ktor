package com.plugins

import com.api.routes.customerAuthRouting
import com.api.routes.customerRouting
import com.api.routes.swaggerRouting
import com.util.constants.Constants
import io.github.smiley4.ktoropenapi.openApi
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*


fun Application.configureRoutes() {

    routing {
        route(Constants.ROUTE_PATH_SWAGGER) {
            openApi()
        }

        swaggerRouting()
        customerAuthRouting()
        //authenticate é configuração do jwt
        //tem que ser mesmo nome configurado no Security
        authenticate(Constants.NAME_AUTH_ROUTE_PROTECTED) {
                customerRouting()
        }
    }
}