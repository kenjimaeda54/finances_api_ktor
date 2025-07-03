package com.data.schema

import com.util.status.StatusTransaction
import com.util.typetransaction.TypeTransaction
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

private const val TRANSACTION_TABLE = "transaction"

private const val HISTORY_TRANSACTION_TABLE = "history"

object TransactionTable: IntIdTable(TRANSACTION_TABLE) {
    val ownerId = varchar("owner_id", length = 255).uniqueIndex()
}

object HistoryTable: IntIdTable(HISTORY_TRANSACTION_TABLE) {
    val transaction = reference("transaction_id",TransactionTable)
    val transactionUuid = uuid("transaction_uuid").autoGenerate().uniqueIndex()
    val date =  datetime("date")
    val status = enumerationByName("status",50,StatusTransaction::class)
    val type = enumerationByName("type",50, TypeTransaction::class)
    val transferTo = varchar("transfer_to", 200).nullable()
    val value =  decimal("value",15,2)
    val entryMoney = bool("is_entry_money")
}