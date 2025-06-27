package com.api.validation

import com.api.dto.FieldError
import com.api.request.CreateCustomerRequest
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.json.Json

fun RequestValidationConfig.validationCustomerRequest() {
    validate<CreateCustomerRequest> { createCustomerRequest ->
        val password = createCustomerRequest.password
        val specialChars = "!@#\$%^&*()_+-=[]{};':\"\\|,.<>/?~"

        val reasons = buildList {
            if (createCustomerRequest.phone.isBlank()) {
                add(
                    FieldError(
                        message = "Phone number cannot be blank",
                        field = "phone"
                    )
                )
            } else if (createCustomerRequest.phone.any { !it.isDigit() }) {
                add(
                    FieldError(
                        message = "Phone number must contain only digits",
                        field = "phone"
                    )
                )
            }

            if (createCustomerRequest.cpf.isBlank()) {
                add(
                    FieldError(
                        message = "CPF cannot blank",
                        field = "cpf"
                    )
                )
            } else {
                val cleanedCpf = createCustomerRequest.cpf.filter { it.isDigit() }
                listOf(
                    (cleanedCpf.length != 11) to "Cpf must contain exactly 11 digits",
                    (cleanedCpf.toSet().size == 1) to "Cpf with all identical digits is invalid"
                ).filter { (checkValid, _) -> checkValid }
                    .forEach { (_, message) ->
                        add(
                            FieldError(
                                message = message,
                                field = "cpf"
                            )
                        )
                    }

            }

            if (password.isBlank()) {
                add(
                    FieldError(
                        message = "Password cannot be blank",
                        field = "password"
                    )
                )
            } else {
                //filter precisa de um predicado sera retornado
                //pela condiçao dentro da lista
                //no forEach ele percorre o Pair dai pega a mensagem
                listOf(
                    (password.length < 8) to "Password must be at least 8 characters long",
                    (!password.any { it.isDigit() }) to "Password must contain at least one digit",
                    (!password.any { it.isLowerCase() }) to "Password must contain at least one lowercase",
                    (!password.any { it.isUpperCase() }) to "Password must contain at least one uppercase",
                    (!password.any { it in specialChars }) to "Password must contain at least one special"
                ).filter { (checkFailed, _) -> checkFailed }
                    .forEach { (_, message) ->
                        add(
                            FieldError(
                                message = message,
                                field = "password"
                            )
                        )
                    }
            }

            if (createCustomerRequest.old <= 0) {
                add(
                    FieldError(
                        message = "Old should be more than 0",
                        field = "old"
                    )
                )
            }

            if (createCustomerRequest.name?.any { it.isDigit() } == true) {
                add(
                    FieldError(
                        message = "Name cannot have number",
                        field = "name"
                    )
                )
            }
        }
        val reasonsAsJson = reasons.map {
            Json.encodeToString(it)
        }
        if (reasons.isNotEmpty()) ValidationResult.Invalid(reasonsAsJson) else ValidationResult.Valid
    }

}