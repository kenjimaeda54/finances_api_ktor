package com.domain.model

data class TransactionHistory (
    val ownerId: String,
    val listHistory: List<History>
)