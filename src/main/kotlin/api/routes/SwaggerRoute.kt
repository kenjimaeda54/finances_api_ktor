package com.api.routes

import com.util.constants.Constants
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.routing.*
import io.github.smiley4.ktoropenapi.route

private const val PATH_SWAGGER_UI = "swagger"

fun Route.swaggerRouting() {

    route(PATH_SWAGGER_UI) {
        swaggerUI("/${Constants.ROUTE_PATH_SWAGGER}") {
        }
    }

}