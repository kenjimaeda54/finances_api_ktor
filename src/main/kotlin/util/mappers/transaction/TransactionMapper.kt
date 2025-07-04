package com.util.mappers.transaction

import com.api.request.TransactionRequest
import com.data.entity.HistoryDAO
import com.data.entity.TransactionDAO
import com.domain.model.History
import com.domain.model.Transaction
import com.domain.model.TransactionHistory
import com.util.extensions.tryParserLocalDateTimeOrReturnLocalDateTimeNow


fun TransactionRequest.toDomain(ownerId: String): Transaction =  Transaction(
    ownerId = ownerId,
    value = this.value,
    transferTo = this.transferTo,
    isTransferToClientFinances = isTransferToClientFinances,
    date = this.date.tryParserLocalDateTimeOrReturnLocalDateTimeNow(),
    type = this.type,
    isEntryMoney = this.isEntryMoney ?: true
)

fun transactionDaoToModel(dao: TransactionDAO): TransactionHistory = TransactionHistory(
    ownerId = dao.ownerId,
    listHistory = dao.history.map(::historyDaoToModel)
)

fun historyDaoToModel(dao: HistoryDAO): History = History(
    value = dao.value,
    date = dao.date,
    type = dao.type,
    status = dao.status,
    transferTo = dao.transferTo,
    isEntryMoney = dao.isEntryMoney,
    isTransferToClientFinances = dao.isTransferToClientFinances


)