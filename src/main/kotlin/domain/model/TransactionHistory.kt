package com.domain.model

import com.util.status.StatusTransaction
import com.util.typetransaction.TypeTransaction
import kotlinx.datetime.LocalDateTime
import java.math.BigDecimal

data class TransactionHistory (
    val ownerId: String,
    val listHistory: List<History>
)

data class History (
    val value: BigDecimal,
    val date: LocalDateTime,
    val type: TypeTransaction,
    val status: StatusTransaction,
    val transferTo: String? = null,
    val isEntryMoney: Boolean
)