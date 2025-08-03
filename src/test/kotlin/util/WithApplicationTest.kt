package util

import com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin
import io.ktor.server.testing.*

fun withApplicationTest(koinModules: List<Module>, block: suspend ApplicationTestBuilder.() -> Unit) {

    testApplication {

        environment {

            config = MapApplicationConfig(
                "jwt.secret" to TestsConstants.JWT_SECRET,
                "jwt.issuer" to TestsConstants.JWT_ISSUER,
                "jwt.audience" to TestsConstants.JWT_AUDIENCE,
                "jwt.realm" to TestsConstants.JWT_REALM,
                "database.url" to "test-database-url",
                "database.user" to "test-database-user",
                "database.password" to "test-database-password"
            )
        }

        application {
            configEnvironment()
            configureSecurity()
            configureSerialization()
            configureRoutes()
            install(Koin) {
                modules(koinModules)
            }
        }
        block()
    }

}