package com.plugins

import com.api.validation.validationCustomerRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureValidation() {
    install(RequestValidation) {
        validationCustomerRequest()

    }
}