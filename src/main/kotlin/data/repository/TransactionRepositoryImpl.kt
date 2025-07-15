package com.data.repository

import com.data.entity.HistoryDAO
import com.data.entity.TransactionDAO
import com.data.schema.CustomerTable
import com.data.schema.HistoryTable
import com.data.schema.TransactionTable
import com.domain.model.History
import com.domain.model.Transaction
import com.domain.model.TransactionHistory
import com.domain.repository.TransactionRepository
import com.util.StatusTransaction
import com.util.suspendTransaction
import com.util.TypeTransaction
import com.util.mappers.transaction.transactionDaoToModel
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.selectAll

class TransactionRepositoryImpl : TransactionRepository {

    override suspend fun createTransaction(transaction: TransactionHistory): Unit = suspendTransaction {
        val transactionDAO = TransactionDAO.new {
            ownerId = transaction.ownerId
        }

        transaction.listHistory.forEach { history ->

            val statusTransaction = when (history.type) {
                TypeTransaction.PIX -> StatusTransaction.COMPLETED
                else -> StatusTransaction.PENDING
            }

            HistoryDAO.new {
                this.transaction = transactionDAO
                value = history.value
                date = history.date
                type = history.type
                status = statusTransaction
                transferTo = history.transferTo ?: ""
                isEntryMoney = history.isEntryMoney
                isTransferToClientFinances = history.isTransferToClientFinances
            }
        }


    }

    override suspend fun retrieveTransactionHistory(ownerId: String): List<TransactionHistory> = suspendTransaction {
        val join = TransactionTable.innerJoin(HistoryTable)
        val query = join
            .select(TransactionTable.columns)
            .where { TransactionTable.ownerId eq ownerId }
            .groupBy(TransactionTable.id)
            .orderBy(HistoryTable.date.max() to SortOrder.DESC)


        val sortedData = query.map { resultRow -> TransactionDAO.wrapRow(resultRow) }.distinct()

        sortedData.map(::transactionDaoToModel)

    }


}