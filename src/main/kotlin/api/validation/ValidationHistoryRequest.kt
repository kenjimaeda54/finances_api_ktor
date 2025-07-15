package com.api.validation

import com.api.dto.FieldError
import com.api.request.HistoryRequest
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

fun RequestValidationConfig.validationHistoryRequest() {
    validate<List<HistoryRequest>>() { listHistoryRequest ->
        val reasons = listHistoryRequest.flatMap { historyRequest ->
            validateSingleHistoryRequest(historyRequest)
        }

        val reasonsAsJson = reasons.map {
            Json.encodeToString(it)
        }

        if (reasons.isNotEmpty()) ValidationResult.Invalid(reasonsAsJson) else ValidationResult.Valid
    }

    validate<HistoryRequest>() { historyRequest ->
        val reasons = validateSingleHistoryRequest(historyRequest)
        val reasonsAsJson = reasons.map {
            Json.encodeToString(it)
        }

        if (reasons.isNotEmpty()) ValidationResult.Invalid(reasonsAsJson) else ValidationResult.Valid

    }

}

private fun validateSingleHistoryRequest(historyRequest: HistoryRequest): List<FieldError> = buildList {
    if (historyRequest.isEntryMoney == false && historyRequest.transferTo.isNullOrEmpty()) {
        add(
            FieldError(
                message = "When is_entry_money is null not permitted transfer_to empty",
                field = "is_entry_money && transfer_to"
            )
        )
    }

    try {
        LocalDateTime.parse(historyRequest.date)

    } catch (_: DateTimeParseException) {
        add(
            FieldError(
                message = "Format date is ISO 8601. Ex: 2025-05-07T15:30",
                field = "date"
            )
        )
    }

    if (historyRequest.isEntryMoney == false && historyRequest.transferTo.isNullOrEmpty()) {
        add(
            FieldError(
                message = "When is_entry_money is false need transfer_to is filled",
                field = "is_entry_money && transfer_to"
            )
        )
    }

}