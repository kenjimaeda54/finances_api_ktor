package com

import com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

//existe uma ordem para as coisas
//ja que meus enviroment esta setando tudo precisa ser primerio
//staus page tem que estar acima de todos
fun Application.module() {
    configEnvironment()
    configureStatusPage()

    configureKoin()
    configureDatabase()
    configureMigration()

    configureSecurity()
    configureSerialization()
    //as rotas precisam de autenticação
    //por isso esta em baixo é o security em cima se não pode acusar
    //erro de AuthenticationHolder
    configureValidation()
    configureRoutes()
    configCors()
    configureSwagger()


}
