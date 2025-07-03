package com.service

import com.api.exception.CustomerNotFoundException
import com.domain.model.Transaction
import com.domain.repository.CustomerRepository
import com.domain.repository.TransactionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class TransactionService : KoinComponent {
    private val customerRepository by inject<CustomerRepository>()
    private val transactionRepository by inject<TransactionRepository>()

    suspend fun handleHistoryTransactions(transaction: Transaction) {

        val customer = customerRepository.findCustomerByUUID(transaction.ownerId)
            ?: throw CustomerNotFoundException(transaction.ownerId)

        val currentBalance = if (transaction.isEntryMoney) {
            customer.balance + transaction.value
        } else {
            customer.balance - transaction.value
        }

        customerRepository.updateAccountBalance(
            customerId = transaction.ownerId,
            balance = currentBalance
        )

        transactionRepository.createTransaction(transaction)

    }

    suspend fun retrieveTransaction(ownerId: Int) = transactionRepository.retrieveTransactionHistory(ownerId)

}

