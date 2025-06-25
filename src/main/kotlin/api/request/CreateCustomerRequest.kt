package com.api.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerRequest (
   val name: String?,
   val cpf: String,
   val phone: String,
   val old: Int,
   val isActive: Boolean? = true,
   val password: String
)