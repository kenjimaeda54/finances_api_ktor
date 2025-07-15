package com.api.exception

import com.api.dto.ErrorDto
import com.util.Error
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.lang.Exception

class NotAcceptedException(message: String) : Exception(message)

fun StatusPagesConfig.handleNotAcceptedException() {
    exception<NotAcceptedException> { call, cause ->
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.NotAcceptable.value.toString(),
            errorCode = Error.ML003.code,
            message = Error.ML003.message.format(cause.message)
        )
        call.respond(
            HttpStatusCode.NotAcceptable,
            errorDto
        )

    }

}