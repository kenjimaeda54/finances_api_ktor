package com.data.entity

import com.data.schema.CustomerTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class CustomerDAO(uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<CustomerDAO>(CustomerTable)

    val uuid by CustomerTable.id
    var name by CustomerTable.name
    var cpf by CustomerTable.cpf
    var phone by CustomerTable.phone
    var old by CustomerTable.old
    var isActive by CustomerTable.isActive
    var password by CustomerTable.password
    var balance by CustomerTable.balance
}