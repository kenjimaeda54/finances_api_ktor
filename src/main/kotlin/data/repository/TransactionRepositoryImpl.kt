package com.data.repository

import com.data.entity.HistoryDAO
import com.data.entity.TransactionDAO
import com.domain.model.Transaction
import com.domain.model.TransactionHistory
import com.domain.repository.TransactionRepository
import com.util.mappers.transaction.transactionDaoToModel
import com.util.StatusTransaction
import com.util.suspendTransaction
import com.util.TypeTransaction
import kotlinx.datetime.toKotlinLocalDateTime

class TransactionRepositoryImpl : TransactionRepository {

    override suspend fun createTransaction(transaction: Transaction): Unit = suspendTransaction {
        val statusTransaction = when (transaction.type) {
            TypeTransaction.PIX -> StatusTransaction.COMPLETED
            else -> StatusTransaction.PENDING
        }
        val transactionDAO = TransactionDAO.new {
            ownerId = transaction.ownerId
        }
        HistoryDAO.new {
            this.transaction = transactionDAO
            value = transaction.value
            date = transaction.date.toKotlinLocalDateTime()
            type = transaction.type
            status = statusTransaction
            transferTo = transaction.transferTo ?: ""
            value = transaction.value
            isEntryMoney = transaction.isEntryMoney
            isTransferToClientFinances = transaction.isTransferToClientFinances
        }
    }


    override suspend fun retrieveTransactionHistory(ownerId: Int): TransactionHistory? = suspendTransaction {
        TransactionDAO.findById(ownerId)?.let(::transactionDaoToModel)
    }


}