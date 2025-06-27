package com.api.exception

import com.api.dto.ErrorDto
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.SerializationException

fun StatusPagesConfig.handleSerializationException() {
    exception<SerializationException> { call, _ ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
            errorCode = "Invalid data format",
            message = "The request body contains incorrectly formatted data",
        )
        call.respond(HttpStatusCode.BadRequest, errorDto)
    }

}