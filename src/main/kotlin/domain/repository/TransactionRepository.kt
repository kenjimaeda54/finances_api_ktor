package com.domain.repository

import com.domain.model.Transaction
import com.domain.model.TransactionHistory

interface TransactionRepository {
    suspend fun createTransaction(transaction: Transaction)
    suspend fun retrieveTransactionHistory(ownerId: Int): TransactionHistory?
}