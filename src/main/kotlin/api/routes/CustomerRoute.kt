package com.api.routes

import com.api.request.CreateCustomerRequest
import com.service.CustomerService
import com.util.customermappers.toDTO
import com.util.customermappers.toModel
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

private const val PATH_ROUTE = "/customers"

fun Route.customerRouting() {
    val customerService: CustomerService by inject()

    route(PATH_ROUTE) {
        get {
            val customers = customerService.findAllUser()
            call.respond(
                customers.map { it.toDTO() },
            )
        }

    }


}