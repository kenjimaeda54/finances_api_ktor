package com

import com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //as rotas precisam de autenticação
    //por isso esta em baixo é o security em cima se não pode acusar
    //erro de AuthenticationHolder
    configEnvironment()
    configureSecurity()
    configCors()
    configureSwagger()
    configureSerialization()
    configureKoin()
    configureDatabase()
    configureMigration()
    configureRoutes()
}
