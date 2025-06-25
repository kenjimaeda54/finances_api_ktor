package com.plugins

import com.util.Environment
import io.ktor.server.application.*


private const val JWT_SECRET = "jwt.secret"
private const val JWT_ISSUER = "jwt.issuer"
private const val JWT_AUDIENCE = "jwt.audience"
private const val JWT_REALM = "jwt.realm"
private const val DATABASE_URL = "database.url"
private const val DATABASE_USER = "database.user"
private const val DATABASE_PASSWORD= "database.password"

fun Application.configEnvironment() {
    val config = environment.config
    val jwtSecret = config.property(JWT_SECRET).getString()
    val jwtIssuer = config.property(JWT_ISSUER).getString()
    val jwtAudience = config.property(JWT_AUDIENCE).getString()
    val jwtRealm = config.property(JWT_REALM).getString()
    val dbUrl = config.property(DATABASE_URL).getString()
    val dbUser = config.property(DATABASE_USER).getString()
    val dbPassword = config.property(DATABASE_PASSWORD).getString()

    Environment.updateJwtRealm(jwtRealm)
    Environment.updateJwtIssuer(jwtIssuer)
    Environment.updateJwtSecret(jwtSecret)
    Environment.updateJwtAudience(jwtAudience)
    Environment.updateDbUrl(dbUrl)
    Environment.updateDbUser(dbUser)
    Environment.updateDbPassword(dbPassword)

}