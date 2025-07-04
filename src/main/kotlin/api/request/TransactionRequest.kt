package com.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.util.BigDecimalSerializer
import com.util.TypeTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransactionRequest (
    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    @SerialName("transfer_to")
    val transferTo: String? = null,
    @SerialName("is_transfer_to_client_finances")
    val isTransferToClientFinances: Boolean? = null,
    //formato 8601
    val date: String,
    val type: TypeTransaction,
    @SerialName("is_entry_money")
    val isEntryMoney: Boolean? = true
)