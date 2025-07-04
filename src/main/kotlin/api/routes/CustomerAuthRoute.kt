package com.api.routes

import com.api.dto.ErrorDto
import com.api.request.CreateCustomerRequest
import com.api.request.CustomerLoginRequest
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.service.CustomerService
import com.util.Environment.jwtAudience
import com.util.Environment.jwtIssuer
import com.util.Environment.jwtSecret
import com.util.Constants
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Constants.PAYLOAD_CLAIM_PHONE
import com.util.Error
import com.util.PasswordHashing
import com.util.mappers.customer.toDomain
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.time.Duration.Companion.hours
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.route

private val TIME_EXPIRATION_JWT = 10.hours

fun Route.customerAuthRouting() {
    val customerService: CustomerService by inject()

    route(Constants.ROUTE_AUTH) {
        post("/login", {
            description = "Retrieve token application"
            request {
                body<CustomerLoginRequest> {
                    example("default") {
                        value = CustomerLoginRequest(
                            phone = "Seu telefone cadastrado",
                            password = "Sua senha cadastrado"
                        )
                    }
                }
            }
            response {
                code(HttpStatusCode.OK) {
                    body<Map<String, String>> {
                        example("default") {
                            value =
                                mapOf("token" to "token to authenticated")
                        }
                    }
                }

            }
        }) {
            val customerLogin = call.receive<CustomerLoginRequest>()
            val user = customerService.findCustomerByPhone(customerLogin.phone)

            if ( !PasswordHashing.verifyPassword(user.password, customerLogin.password)) {
                val error = ErrorDto(
                    httpStatusCode = HttpStatusCode.Unauthorized.toString(),
                    errorCode = Error.ML100.code,
                    message = Error.ML100.message
                )
                call.respond(HttpStatusCode.Unauthorized, error)
            } else {
                val token = JWT.create()
                    .withAudience(jwtAudience.value)
                    .withIssuer(jwtIssuer.value)
                    .withClaim(PAYLOAD_CLAIM_PHONE,user.phone)
                    .withClaim(PAYLOAD_CLAIM_ID,user.uuid)
                    .withExpiresAt(Date(System.currentTimeMillis() + TIME_EXPIRATION_JWT.inWholeMilliseconds))
                    .sign(Algorithm.HMAC256(jwtSecret.value))

                call.respond(HttpStatusCode.OK, mapOf("token" to token))

            }

        }

        post("/register", {
            description = "Create user"
            request {
                body<CreateCustomerRequest> {
                   example("Default"){
                       value = CreateCustomerRequest(
                           name = "Your name",
                           cpf = "Your cpf",
                           phone = "Your phone",
                           old = 10,
                           password = "Your better password"
                       )
                   }
                }
            }
            response {
                HttpStatusCode.Created to {
                }
            }
        }) {
            //as validações estão nas camadas de validação
            //não pode colcoar try catch aqui, pois assim
            //não sera lançado o erro da camada responsavel
            val customer = call.receive<CreateCustomerRequest>()
            customerService.addCustomer(customer.toDomain())
            call.respond(HttpStatusCode.Created)

        }
    }


}