package com.data.entity

import com.data.schema.CustomerTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CustomerDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerDAO>(CustomerTable)

    var name by CustomerTable.name
    var cpf by CustomerTable.cpf
    var phone by CustomerTable.phone
    var old by CustomerTable.old
    var isActive by CustomerTable.isActive
    var password by CustomerTable.password
}