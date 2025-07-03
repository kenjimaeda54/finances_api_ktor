package com.api.dto

import com.util.bigdecimal.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CustomerDto(
    val uuid: String,
    val name: String?,
    val cpf: String,
    val phone: String,
    val old: Int,
    val isActive: Boolean,
    @Serializable(with = BigDecimalSerializer::class)
    val balance: BigDecimal
)
