package com.util.customermappers

import com.api.dto.CustomerDto
import com.api.request.CreateCustomerRequest
import com.data.entity.CustomerDAO
import com.domain.model.Customer
import com.util.hashing.PasswordHashing

fun Customer.toDTO() = CustomerDto(
    name = this.name,
    cpf = this.cpf,
    phone = this.phone,
    old = this.old,
    isActive = this.isActive
)

fun CreateCustomerRequest.toModel() = Customer(
    name = this.name,
    cpf = this.cpf,
    phone = this.phone,
    old = this.old,
    isActive =  true,
    password = PasswordHashing.hashingPassword(this.password)
)

fun customerDaoToModel(dao: CustomerDAO): Customer = Customer(
    name = dao.name,
    cpf = dao.cpf,
    phone = dao.phone,
    old = dao.old,
    isActive = dao.isActive,
    password = dao.password
)