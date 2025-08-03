package util

import com.api.dto.CustomerDto
import com.api.request.CreateCustomerRequest
import kotlinx.serialization.json.Json

object TestUtilsJson {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
    }

    val createCustomer: CreateCustomerRequest = loadJsonFromResources(
        "mocks/customer.request.json"
    )

    val customerDto: CustomerDto = loadJsonFromResources(
        "mocks/customer.dto.json"
    )


    //reified seria mais ligado a performace
    //exemplo verficiar o tipo em tempo de excução
    //acessar a classe do tipo generico
    private inline fun <reified T> loadJsonFromResources(path: String): T {
        val jsonString = this::class.java.classLoader
            .getResource(path)?.readText() ?: throw IllegalArgumentException("Arquivo não encontrado")

        return jsonParser.decodeFromString<T>(jsonString)

    }


}