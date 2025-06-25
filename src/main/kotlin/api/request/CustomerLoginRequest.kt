package com.api.request

import kotlinx.serialization.Serializable

@Serializable
data class CustomerLoginRequest(
    val phone: String,
    val password: String,
)
