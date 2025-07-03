package com.service

import com.api.exception.CustomerNotFoundException
import com.api.exception.CustomerNotFoundPhoneException
import com.domain.model.Customer
import com.domain.repository.CustomerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class CustomerService: KoinComponent {
    private val customerRepository: CustomerRepository by inject()

    suspend fun addCustomer(customer: Customer) = customerRepository.addCustomer(customer)

    suspend fun findCustomerByPhone(phone: String): Customer  {
        val customer = customerRepository.findCustomerByPhone(phone) ?: throw  CustomerNotFoundPhoneException(
            phone
        )
        return customer
    }

    suspend fun findCustomerByUUID(uuid: String): Customer  {
         val customer = customerRepository.findCustomerByUUID(uuid) ?: throw  CustomerNotFoundException(
            uuid
         )
         return customer

    }

}