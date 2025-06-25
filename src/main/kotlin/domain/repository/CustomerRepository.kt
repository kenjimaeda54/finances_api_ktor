package com.domain.repository

import com.domain.model.Customer

interface CustomerRepository {
    suspend fun findAll(): List<Customer>
    suspend fun addCustomer(customer: Customer)
    suspend fun findCustomerByPhone(phone: String): Customer?
}