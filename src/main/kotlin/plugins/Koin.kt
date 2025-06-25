package com.plugins

import com.data.repository.CustomerRepositoryImpl
import com.domain.repository.CustomerRepository
import com.service.CustomerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            repositoryModule,
            serviceModule
        )

    }
}

private val serviceModule = module {
    single<CustomerService> { CustomerService()  }
}

private val repositoryModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl()  }

}
