package com.api.exception

import com.api.dto.ErrorDto
import com.util.error.Error
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

class CustomerNotFoundException(customerId: String) : Exception(customerId)

class CustomerNotFoundPhoneException(customerPhone: String) : Exception(customerPhone)

fun StatusPagesConfig.handleCustomerNotFoundException() {
    exception<CustomerNotFoundException> { call, cause ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.NotFound.value.toString(),
            errorCode = Error.ML201.code,
            message = Error.ML201.message.format(cause.message)
        )
        call.respond(
            HttpStatusCode.NotFound,
            errorDto
        )

    }

    exception<CustomerNotFoundPhoneException> { call, cause ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.NotFound.value.toString(),
            errorCode = Error.ML202.code,
            message = Error.ML202.message.format(cause.message)
        )
        call.respond(
            HttpStatusCode.NotFound,
            errorDto
        )

    }

}