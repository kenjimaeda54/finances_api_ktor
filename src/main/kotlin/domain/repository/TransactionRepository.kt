package com.domain.repository

import com.domain.model.History
import com.domain.model.Transaction
import com.domain.model.TransactionHistory

interface TransactionRepository {
    suspend fun createTransaction(transaction: TransactionHistory)
    suspend fun retrieveTransactionHistory(ownerId: String): List<TransactionHistory>
}