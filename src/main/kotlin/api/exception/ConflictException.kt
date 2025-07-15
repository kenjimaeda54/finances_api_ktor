package com.api.exception

import com.api.dto.ErrorDto
import com.util.Error
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

class ConflictException(message: String): Exception(message)


fun  StatusPagesConfig.handleConflictException() {
    exception<ConflictException>(){ call,cause ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
            errorCode = Error.ML203.code,
            message = Error.ML203.message.format(cause.message)
        )
        call.respond(
            HttpStatusCode.BadRequest,
            errorDto
        )

    }
}