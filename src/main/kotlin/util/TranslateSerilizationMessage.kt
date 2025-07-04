package com.util

fun translateSerializationMessage(message: String?): String {
    if (message == null) return "Invalid request format."
    return when {
        message.startsWith("Unknown key") -> {
            val key = message.substringAfter("'").substringBefore("'")
            "The '$key' field is not recognized. Please remove it from your request."
        }

        message.startsWith("Field") && message.contains("is missing") -> {
            val key = message.substringAfter("'").substringBefore("'")
            "The required field '$key' is missing from your request."
        }

        message.contains("is not a valid") -> {
            // Ex: "Value 'abc' for field 'age' is not a valid 'kotlin.Int'"
            val value = message.substringAfter("Value '").substringBefore("'")
            val key = message.substringAfter("field '").substringBefore("'")
            "The value '$value' provided for field '$key' is not in the correct format."
        }

        message.startsWith("Illegal input") -> {
            "The request contains invalid data. Please check the format of the request body."
        }

        else -> {
            "The request contains an invalid format. Technical detail: $message"
        }
    }
}
