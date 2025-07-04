package com.api.exception

import com.api.dto.ErrorDto
import com.api.dto.FieldError
import com.util.Error
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
            errorCode = Error.ML101.code,
            message = Error.ML101.message,
            fieldErrors = fieldErrors
        )
        call.respond(HttpStatusCode.BadRequest, errorDto)
    }

    //uma maneira descobrir a classe que esta lançando erro
//    exception<Throwable> { call,cause ->
//        println("Caught exception: ${cause.javaClass.name}: ${cause.message}")
//
//    }
}