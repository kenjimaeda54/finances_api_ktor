package com.data.repository

import com.data.entity.CustomerDAO
import com.data.schema.CustomerTable
import com.domain.model.Customer
import com.domain.repository.CustomerRepository
import com.util.customermappers.customerDaoToModel
import com.util.suspendTransaction

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun findAll(): List<Customer> = suspendTransaction {
        CustomerDAO.all().map(::customerDaoToModel)
    }

    override suspend fun addCustomer(customer: Customer): Unit = suspendTransaction {
        CustomerDAO.new {
            name = customer.name
            phone = customer.phone
            cpf = customer.cpf
            old = customer.old
            isActive = customer.isActive
            password = customer.password

        }
    }

    override suspend fun findCustomerByPhone(phone: String): Customer? = suspendTransaction {
        CustomerDAO.find {
            (CustomerTable.phone eq phone)
        }
            .limit(1)
            .map(::customerDaoToModel)
            .firstOrNull()
    }

}