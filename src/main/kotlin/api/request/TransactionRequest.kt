package com.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.util.bigdecimal.BigDecimalSerializer
import com.util.typetransaction.TypeTransaction
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TransactionRequest (
    @JsonProperty("owner_id")
    val ownerId: String,
    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    @JsonProperty("transfer_to")
    val transferTo: String? = null,
    @JsonProperty("is_transfer_to_client_finances")
    val isTransferToClientFinances: Boolean? = null,
    //formato 8601
    val date: String,
    val type: TypeTransaction,
    @JsonProperty("is_entry_money")
    val isEntryMoney: Boolean? = true
)