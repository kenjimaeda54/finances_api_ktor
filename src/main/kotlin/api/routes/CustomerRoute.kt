package com.api.routes

import com.api.dto.CustomerDto
import com.api.dto.ErrorDto
import com.service.CustomerService
import com.util.Constants
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Error
import com.util.mappers.customer.toDTO
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.route
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Route.customerRouting() {
    val customerService: CustomerService by inject()

    route(Constants.ROUTE_CUSTOMER, {
        protected = true
    }) {
        get("", {
            description = "Retrieve all customer"
            response {
                code(HttpStatusCode.OK) {
                    body<CustomerDto> {
                        example(
                            "default",
                        ) {
                            value =
                                """
                               {
                                 "uuid": "b10cb4d2-d25e0566821",
                                 "name": "Kenji",
                                 "cpf": "334603",
                                 "phone": "359316",
                                 "old": 10,
                                 "isActive": true,
                                 "balance": "0.00"
                               }
                                """.trimIndent()
                        }
                    }
                }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()
            val customerUUID = principal?.payload?.getClaim(PAYLOAD_CLAIM_ID)?.asString()

            if (customerUUID == null) {
                val errorDto = ErrorDto(
                    httpStatusCode = HttpStatusCode.Unauthorized.value.toString(),
                    errorCode = Error.ML000.code,
                    message = Error.ML000.message
                )
                call.respond(HttpStatusCode.Unauthorized, errorDto)
                return@get
            }
            val customer = customerService.findCustomerByUUID(customerUUID)

            call.respond(HttpStatusCode.OK, customer.toDTO())
        }

    }


}