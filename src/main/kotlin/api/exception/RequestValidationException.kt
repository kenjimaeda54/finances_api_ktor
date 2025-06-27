package com.api.exception

import com.api.dto.ErrorDto
import com.api.dto.FieldError
import io.ktor.http.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json

fun StatusPagesConfig.handleRequestValidationException() {
    exception<RequestValidationException> { call, cause ->
        val fieldErrors = cause.reasons.map { reasonJson ->
            try {
                Json.decodeFromString<FieldError>(reasonJson)
            } catch (e: Exception) {
                FieldError(
                    field = "unknown",
                    message = reasonJson
                )
            }
        }
        val errorDto = ErrorDto(
            httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
            errorCode = "Validation error",
            message = "Your body contains error validation",
            fieldErrors = fieldErrors
        )
        call.respond(HttpStatusCode.BadRequest, errorDto)


    }
}