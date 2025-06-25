package com.plugins

import io.ktor.server.application.*
import org.flywaydb.core.Flyway

fun Application.configureMigration() {
    val environment = environment.config
    val dbUrl = environment.property("database.url").getString()
    val dbUser = environment.property("database.user").getString()
    val dbPassword = environment.property("database.password").getString()

    val flyway = Flyway.configure()
        .dataSource(dbUrl,dbUser,dbPassword)
        .locations("db/migration")
        .load()

    try {
        flyway.migrate()
        log.error("Flyway migrations applicable successfully ")
    }catch (e: Exception){
        log.error("Flyway migrations failed: ${e.message}")
    }
}