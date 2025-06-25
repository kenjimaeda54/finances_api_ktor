package com.plugins

import com.util.Environment.dbPassword
import com.util.Environment.dbUrl
import com.util.Environment.dbUser
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Database.connect(
        dbUrl.value,
        user = dbUser.value,
        password = dbPassword.value
    )

}