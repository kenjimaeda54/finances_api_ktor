package com.plugins

import com.api.exception.handleCustomerNotFoundException
import com.api.exception.handleRequestValidationException
import com.api.exception.handleSerializationException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        handleSerializationException()
        handleRequestValidationException()
        handleCustomerNotFoundException()
    }
}