package com.api.exception

import com.api.dto.ErrorDto
import com.util.Error
import com.util.translateSerializationMessage
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.handleBadRequestException(){
    exception<BadRequestException>{ call, cause ->
        val detailMessage = cause.cause?.message ?: cause.message
        val friendlyMessage = translateSerializationMessage(detailMessage)

        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
            errorCode = Error.ML002.code,
            message = Error.ML002.message.format(friendlyMessage)
        )
        call.respond(
            HttpStatusCode.BadRequest,
            errorDto
        )
    }
}