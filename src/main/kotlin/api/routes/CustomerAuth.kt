package com.api.routes

import com.api.request.CreateCustomerRequest
import com.api.request.CustomerLoginRequest
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.service.CustomerService
import com.util.Environment.jwtAudience
import com.util.Environment.jwtIssuer
import com.util.Environment.jwtSecret
import com.util.customermappers.toModel
import com.util.hashing.PasswordHashing
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.time.Duration.Companion.hours


private  val TIME_EXPIRATION_JWT = 10.hours

fun Route.customerAuth() {
    val customerService: CustomerService by inject()

    post("/login") {
        val customerLogin = call.receive<CustomerLoginRequest>()
        val user = customerService.findCustomerByPhone(customerLogin.phone)

        if (user == null || !PasswordHashing.verifyPassword(user.password, customerLogin.password)) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("message" to "Invalid credentials"))
        } else {
            val token = JWT.create()
                .withAudience(jwtAudience.value)
                .withIssuer(jwtIssuer.value)
                .withClaim("customerPhone", user.phone)
                .withExpiresAt(Date(System.currentTimeMillis() + TIME_EXPIRATION_JWT.inWholeMilliseconds))
                .sign(Algorithm.HMAC256(jwtSecret.value))

            call.respond(HttpStatusCode.OK, mapOf("token" to token))

        }

    }

    post("/register") {
        try {
            val customer = call.receive<CreateCustomerRequest>()
            customerService.addCustomer(customer.toModel())
            call.respond(HttpStatusCode.Created)
        }catch (ex: IllegalStateException) {
            call.respond(HttpStatusCode.BadRequest)
        } catch (ex: JsonConvertException) {
            call.respond(HttpStatusCode.BadRequest)
        }catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Internal server error: ${ex.message}"))
        }
    }



}