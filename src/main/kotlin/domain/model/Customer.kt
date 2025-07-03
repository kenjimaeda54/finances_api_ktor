package com.domain.model

import java.math.BigDecimal
import java.util.UUID

data class Customer (
    val uuid: String? = null,
    val name: String? = null,
    val cpf: String,
    val phone: String,
    val old: Int,
    val isActive:Boolean,
    val password: String,
    val balance: BigDecimal
)