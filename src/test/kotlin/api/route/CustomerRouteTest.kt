package api.route

import com.api.dto.CustomerDto
import com.domain.model.Customer
import com.service.CustomerService
import com.util.Constants
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import util.TestUtilsJson
import util.generateTestToken
import util.withApplicationTest
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerRouteTest {

    private val mockService = mockk<CustomerService>()
    private val testModule = module {
        single { mockService }
    }
    private val customer = Customer(
        uuid = "33434wrr12334",
        name = "",
        cpf = "",
        phone = "359973281",
        old = 10,
        isActive = true,
        password = "",
        balance = BigDecimal("0.0")
    )

    @Test
    fun `GET customer should return DTO is expected`()  {
        val expectedDto: CustomerDto = TestUtilsJson.customerDto

        coEvery {
            mockService.findCustomerByUUID(any())
        } returns customer

        withApplicationTest(listOf(testModule)) {
            val token = generateTestToken(customerId = customer.uuid ?: "", phone = customer.phone)

            val response = client.get(Constants.ROUTE_CUSTOMER) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            val actualDto = Json.decodeFromString<CustomerDto>(response.bodyAsText())

            assertEquals(
                HttpStatusCode.OK.value, response.status.value
            )
            assertEquals(
                expectedDto, actualDto
            )

        }

    }

}