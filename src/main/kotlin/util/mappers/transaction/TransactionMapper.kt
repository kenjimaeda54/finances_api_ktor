package com.util.mappers.transaction

import com.api.dto.HistoryDto
import com.api.request.HistoryRequest
import com.data.entity.HistoryDAO
import com.data.entity.TransactionDAO
import com.domain.model.History
import com.domain.model.TransactionHistory
import com.util.extensions.tryParserLocalDateTimeOrReturnLocalDateTimeNow
import kotlinx.datetime.LocalDateTime
import java.time.LocalDateTime as JavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime


fun HistoryRequest.toDomain(): History = History(
    value = this.value,
    date = this.date.tryParserLocalDateTimeOrReturnLocalDateTimeNow()?.toKotlinLocalDateTime()
        ?: JavaLocalDateTime.now().toKotlinLocalDateTime(),
    type = this.type,
    transferTo = this.transferTo,
    isEntryMoney = this.isEntryMoney ?: true,
    isTransferToClientFinances = this.isTransferToClientFinances ?: false

)

fun History.toDTO() = HistoryDto(
    value = this.value,
    date = this.date.toString(),
    type = this.type,
    transferTo = this.transferTo
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