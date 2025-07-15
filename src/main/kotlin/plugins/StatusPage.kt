package com.plugins

import com.api.exception.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        handleNotAcceptedException()
        handleBadRequestException()
        handleCustomerNotFoundException()
        handleConflictException()
    }
}