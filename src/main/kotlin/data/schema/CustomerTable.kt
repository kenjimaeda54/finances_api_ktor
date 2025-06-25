package com.data.schema

import org.jetbrains.exposed.dao.id.IntIdTable

private const val CUSTOMER_DATABASE = "customer"

object CustomerTable: IntIdTable(CUSTOMER_DATABASE) {
    val name = varchar("name",50).nullable()
    val cpf = varchar("cpf", length = 50).uniqueIndex()
    val phone = varchar("phone", length = 50).uniqueIndex()
    val old =  integer("old")
    val isActive = bool("is_active")
    val password = varchar("password", length = 255)
}