package com.plugins

import com.data.repository.CustomerRepositoryImpl
import com.data.repository.TransactionRepositoryImpl
import com.domain.repository.CustomerRepository
import com.domain.repository.TransactionRepository
import com.service.CustomerService
import com.service.TransactionService
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
    single { CustomerService() }
    single { TransactionService() }
}

private val repositoryModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<TransactionRepository> { TransactionRepositoryImpl() }

}
