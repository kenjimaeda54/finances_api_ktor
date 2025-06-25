package com.domain.model

data class Customer (
    val name: String? = null,
    val cpf: String,
    val phone: String,
    val old: Int,
    val isActive:Boolean,
    val password: String,
)