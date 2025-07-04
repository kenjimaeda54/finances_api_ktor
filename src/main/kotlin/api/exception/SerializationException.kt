package com.api.exception

import com.api.dto.ErrorDto
import com.util.Error
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.SerializationException

fun StatusPagesConfig.handleSerializationException() {
    exception<SerializationException> { call, _ ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
            errorCode = Error.ML100.code,
            message =  Error.ML100.message,
        )
        call.respond(HttpStatusCode.BadRequest, errorDto)
    }

}