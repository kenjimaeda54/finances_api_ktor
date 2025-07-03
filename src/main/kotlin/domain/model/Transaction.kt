package com.domain.model

import com.util.typetransaction.TypeTransaction
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction (
    val ownerId: String,
    val value: BigDecimal,
    val transferTo: String? = null,
    val isTransferToClientFinances: Boolean? = null,
    val date: LocalDateTime,
    val type: TypeTransaction,
    val isEntryMoney: Boolean,
)