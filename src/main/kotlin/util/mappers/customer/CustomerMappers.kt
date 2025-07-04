package com.util.mappers.customer

import com.api.dto.CustomerDto
import com.api.request.CreateCustomerRequest
import com.data.entity.CustomerDAO
import com.domain.model.Customer
import com.util.PasswordHashing
import java.math.BigDecimal

fun Customer.toDTO() = CustomerDto(
    name = this.name,
    cpf = this.cpf,
    phone = this.phone,
    old = this.old,
    isActive = this.isActive,
    balance = this.balance,
    uuid = this.uuid ?: ""
)

fun CreateCustomerRequest.toDomain() = Customer(
    name = this.name,
    cpf = this.cpf,
    phone = this.phone,
    old = this.old,
    isActive =  true,
    password = PasswordHashing.hashingPassword(this.password),
    balance = BigDecimal(0.0)
)

fun customerDaoToModel(dao: CustomerDAO): Customer = Customer(
    name = dao.name,
    cpf = dao.cpf,
    phone = dao.phone,
    old = dao.old,
    isActive = dao.isActive,
    password = dao.password,
    balance = dao.balance,
    uuid = dao.uuid.toString()
)