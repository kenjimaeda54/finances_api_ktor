package com.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto (
  @JsonProperty("http_status_code")
  val httpStatusCode: String,
  @JsonProperty("error_code")
  val errorCode: String,
  val message: String,
  @JsonProperty("field_errors")
  val fieldErrors: List<FieldError>? = null
)


@Serializable
data class FieldError(
    val message: String,
    val field: String
)