package com.util.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeParseException

fun String.tryParserLocalDateTimeOrReturnLocalDateTimeNow(): LocalDateTime? {
    return try {
       LocalDateTime.parse(this)
    }catch (exception: DateTimeParseException) {
        null
    }
}