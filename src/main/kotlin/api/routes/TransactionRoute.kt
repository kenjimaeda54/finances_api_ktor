package com.api.routes

import com.api.request.TransactionRequest
import com.service.TransactionService
import com.util.constants.Constants
import com.util.mappers.transaction.toDomain
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.route
import io.ktor.http.*
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
                               "owner_id": "Id who is transferring",
                               "value": "12.00",  
                               "transfer_to": "Identifier for the person transferring",
                               "is_transfer_to_client_finances": false,
                               "date": "2024-07-02T15:30:00",
                               "type": "PIX",
                               "isEntryMoney": false
                            }
                            """.trimIndent()
                    }
                }
            }
            response {
                HttpStatusCode.Created to { }
            }
        }) {
            val transfer = call.receive<TransactionRequest>()
            transactionService.handleHistoryTransactions(transfer.toDomain())
            call.respond(HttpStatusCode.Created)

        }


    }


}