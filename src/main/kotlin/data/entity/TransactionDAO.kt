package com.data.entity

import com.data.schema.HistoryTable
import com.data.schema.TransactionTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TransactionDAO(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TransactionDAO>(TransactionTable)

    var ownerId by  TransactionTable.ownerId
    val history by  HistoryDAO referrersOn  HistoryTable.transaction

}

class  HistoryDAO(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<HistoryDAO>(HistoryTable)

    var transaction by TransactionDAO referencedOn  HistoryTable.transaction
    var date by HistoryTable.date
    var status by HistoryTable.status
    var type by HistoryTable.type
    var transferTo by HistoryTable.transferTo
    var value by HistoryTable.value
    var isEntryMoney by HistoryTable.entryMoney

}