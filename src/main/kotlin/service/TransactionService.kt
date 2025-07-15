package com.service

import com.api.exception.CustomerNotFoundException
import com.api.exception.NotAcceptedException
import com.domain.model.History
import com.domain.model.TransactionHistory
import com.domain.repository.CustomerRepository
import com.domain.repository.TransactionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.math.BigDecimal

class TransactionService : KoinComponent {
    private val customerRepository by inject<CustomerRepository>()
    private val transactionRepository by inject<TransactionRepository>()

    suspend fun handleCreateHistoryTransactions(transaction: TransactionHistory) {


        val customer = customerRepository.findCustomerByUUID(transaction.ownerId)
            ?: throw CustomerNotFoundException(transaction.ownerId)

        val currentHistoryValue = transaction.listHistory.fold(BigDecimal.ZERO) { accumulator, history ->
            if (history.isEntryMoney) {
                accumulator + history.value

            } else {
                accumulator - history.value
            }
        }

        val finalBalance = customer.balance + currentHistoryValue

        if (finalBalance < BigDecimal.ZERO) {
            throw NotAcceptedException("Customer with negative balance is not allowed")
        }

        customerRepository.updateAccountBalance(
            customerId = transaction.ownerId,
            balance = finalBalance
        )

        transactionRepository.createTransaction(transaction)

    }

    suspend fun retrieveTransaction(ownerId: String): List<History> {
      val listTransactionHistory = transactionRepository.retrieveTransactionHistory(ownerId)

       return listTransactionHistory. flatMap { transaction -> transaction.listHistory.map { history ->
           History(
               value = history.value,
               date = history.date,
               type = history.type,
               status = history.status,
               transferTo =  history.transferTo,
               isEntryMoney = history.isEntryMoney,
               isTransferToClientFinances = history.isTransferToClientFinances
           )

       }}
    }


}

