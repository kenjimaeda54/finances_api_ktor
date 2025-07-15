package com.api.exception

import com.api.dto.ErrorDto
import com.api.dto.FieldError
import com.util.Error
import com.util.translateSerializationMessage
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun StatusPagesConfig.handleBadRequestException(){
    exception<Throwable>{ call, cause ->

        if (cause is BadRequestException) {
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

        if (cause is RequestValidationException) {
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

        if (cause is SerializationException) {
            val errorDto = ErrorDto(
                httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
                errorCode = Error.ML100.code,
                message =  Error.ML100.message,
            )
            call.respond(HttpStatusCode.BadRequest, errorDto)
        }

        if (cause is ExposedSQLException) {
            val detailMessage = cause.cause?.message ?: cause.message
            val friendlyMessage = translateSerializationMessage(detailMessage)

            val errorDto = ErrorDto(
                httpStatusCode = HttpStatusCode.BadRequest.value.toString(),
                errorCode = Error.ML203.code,
                message = Error.ML203.message.format(friendlyMessage)
            )
            call.respond(
                HttpStatusCode.BadRequest,
                errorDto
            )
        }


    }

    //uma maneira descobrir a classe que esta lançando erro
//    exception<Throwable> { call,cause ->
//        println("Caught exception: ${cause.javaClass.name}: ${cause.message}")
//
//    }
}