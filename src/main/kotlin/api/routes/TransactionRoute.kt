package com.api.routes

import com.api.dto.ErrorDto
import com.api.request.TransactionRequest
import com.service.TransactionService
import com.util.Constants
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Error
import com.util.mappers.transaction.toDomain
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.route
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.transactionsRouting() {
    val transactionService by inject<TransactionService>()

    route(Constants.ROUTE_TRANSACTIONS, {
        protected = true
    }) {

        post("", {
            description = "Transfer money"
            request {
                body<TransactionRequest> {
                    example("default") {

                        value =
                            """
                            { 
                               "value": "12.00",  
                               "transfer_to": "Identifier for the person transferring",
                               "is_transfer_to_client_finances": false,
                               "date": "2024-07-02T15:30:00",
                               "type": "PIX",
                               "is_entry_money": false
                            }
                            """.trimIndent()
                    }
                }
            }
            response {
                HttpStatusCode.Created to { }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()
            val ownerId = principal?.payload?.getClaim(PAYLOAD_CLAIM_ID)?.asString()
            if (ownerId == null) {
                val errorDto = ErrorDto(
                    httpStatusCode = HttpStatusCode.Unauthorized.value.toString(),
                    errorCode = Error.ML00.code,
                    message = Error.ML00.message
                )
                call.respond(HttpStatusCode.Unauthorized, errorDto)
                return@post
            }
            val transfer = call.receive<TransactionRequest>()
            transactionService.handleHistoryTransactions(transfer.toDomain(ownerId))
            call.respond(HttpStatusCode.Created)

        }


    }


}