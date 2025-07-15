package com.api.routes

import com.api.dto.ErrorDto
import com.api.request.HistoryRequest
import com.domain.model.History
import com.domain.model.TransactionHistory
import com.service.TransactionService
import com.util.Constants
import com.util.Constants.PAYLOAD_CLAIM_ID
import com.util.Error
import com.util.mappers.transaction.toDTO
import com.util.mappers.transaction.toDomain
import io.github.smiley4.ktoropenapi.get
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
                body<List<HistoryRequest>> {
                    example("default") {

                        value =
                            """
                            [
                                { 
                                    "value": "12.00",  
                                    "transfer_to": "Identifier for the person transferring",
                                    "is_transfer_to_client_finances": false,
                                    "date": "2024-07-02T15:30:00",
                                    "type": "PIX",
                                    "is_entry_money": false
                                }
                                
                            ]
                            
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
                    errorCode = Error.ML000.code,
                    message = Error.ML000.message
                )
                call.respond(HttpStatusCode.Unauthorized, errorDto)
                return@post
            }
            val transfer = call.receive<List<HistoryRequest>>()
            val transactionHistory = TransactionHistory(
                ownerId = ownerId,
                listHistory = transfer.map {
                    it.toDomain()
                }
            )
            transactionService.handleCreateHistoryTransactions(transactionHistory)
            call.respond(HttpStatusCode.Created)

        }

        get("", {
            description = "Retrieve all transactions"
            response {
                code(HttpStatusCode.OK) {
                    body<List<History>>() {
                        example("default") {
                            value =
                                """
                              [
                                 {
                                    "value": "0.0",
                                    "date": "2025-12-02T12:32:00",
                                    "type": "PIX",
                                    "transfer_to": "34332"
                                 }
                              ]  
                                
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

            val transactions = transactionService.retrieveTransaction(customerUUID)
            val transactionDto = transactions.map {
                it.toDTO()
            }
            call.respond(HttpStatusCode.OK, transactionDto)

        }

    }


}