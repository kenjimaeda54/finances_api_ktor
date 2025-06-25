package com.service

import com.domain.model.Customer
import com.domain.repository.CustomerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CustomerService: KoinComponent {
    private val customerRepository: CustomerRepository by inject()

    suspend fun findAllUser() = customerRepository.findAll()

    suspend fun addCustomer(customer: Customer) = customerRepository.addCustomer(customer)

    suspend fun findCustomerByPhone(phone: String): Customer? =  customerRepository.findCustomerByPhone(phone)

}