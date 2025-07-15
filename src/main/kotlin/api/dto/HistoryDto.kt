package com.api.dto

import com.util.BigDecimalSerializer
import com.util.TypeTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class HistoryDto(
    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    val date: String,
    val type: TypeTransaction,
    @SerialName("transfer_to")
    val transferTo: String? = null,

    )