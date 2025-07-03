package com.domain.repository

import com.domain.model.Customer
import java.math.BigDecimal
import java.util.UUID

interface CustomerRepository {
    suspend fun findAll(): List<Customer>
    suspend fun addCustomer(customer: Customer)
    suspend fun findCustomerByPhone(phone: String): Customer?
    suspend fun updateAccountBalance(customerId: String,balance: BigDecimal)
    suspend fun findCustomerByUUID(uuid: String): Customer?
}