package com.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto(
    val name: String?,
    val cpf: String,
    val phone: String,
    val old: Int,
    val isActive: Boolean
)
