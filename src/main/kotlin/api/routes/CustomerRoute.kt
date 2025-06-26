package com.api.routes

import com.api.dto.CustomerDto
import com.domain.model.Customer
import com.service.CustomerService
import com.util.constants.Constants
import com.util.customermappers.toDTO
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.route
import io.ktor.http.*


fun Route.customerRouting() {
    val customerService: CustomerService by inject()

    route(Constants.ROUTE_CUSTOMER,{
        protected = true
    }) {
        get("", {
            description = "Retrieve all customer"
            response {
                code(HttpStatusCode.OK) {
                    body<List<CustomerDto>> {
                        example(
                            "default",
                        ) {
                            value = listOf(
                                CustomerDto(
                                    name = "",
                                    cpf = "",
                                    phone = "",
                                    old = 0,
                                    isActive = true
                                )
                            )
                        }
                    }
                }
            }
        }) {
            val customers = customerService.findAllUser()
            call.respond(
                customers.map { it.toDTO() },
            )
        }

    }


}