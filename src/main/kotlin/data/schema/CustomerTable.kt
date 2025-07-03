package com.data.schema

import com.data.schema.HistoryTable.autoGenerate
import com.data.schema.HistoryTable.uniqueIndex
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable

private const val CUSTOMER_DATABASE = "customer"

object CustomerTable: UUIDTable(CUSTOMER_DATABASE, columnName = "uuid") {
    val name = varchar("name",50).nullable()
    val cpf = varchar("cpf", length = 50).uniqueIndex()
    val phone = varchar("phone", length = 50).uniqueIndex()
    val old =  integer("old")
    val isActive = bool("is_active")
    val password = varchar("password", length = 255)
    val balance = decimal("balance",11,2)
}