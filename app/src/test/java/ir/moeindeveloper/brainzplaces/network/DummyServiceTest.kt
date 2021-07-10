package ir.moeindeveloper.brainzplaces.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.whatif.whatIfNotNull
import io.kotest.matchers.shouldBe
import ir.moeindeveloper.brainzplaces.MainCoroutinesRule
import ir.moeindeveloper.brainzplaces.assertForeach
import ir.moeindeveloper.brainzplaces.mocks.MockResponses
import ir.moeindeveloper.brainzplaces.mocks.dummyDataResponse
import ir.moeindeveloper.brainzplaces.network.service.DummyService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 *  Dummy service test, just for basic sample
 *  :)
 */
class DummyServiceTest : ServiceTester<DummyService>() {

    private lateinit var service: DummyService

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(DummyService::class.java)
    }

    @Test
    @Throws(IOException::class)
    fun `Dummy service result test`(): Unit = runBlocking {
        enqueueResponse(dummyDataResponse)

        val expectedResult = MockResponses.dummyResponse

        val actualResult = service.getDummies()

        webServer.takeRequest()

        (actualResult as ApiResponse.Success).data.whatIfNotNull { entities ->
            entities.assertForeach { position ->
                val expectedItem = expectedResult[position]

                id shouldBe expectedItem.id
                firstName shouldBe expectedItem.firstName
                lastName shouldBe expectedItem.lastName
                email shouldBe expectedItem.email
                gender shouldBe expectedItem.gender
                ipAddress shouldBe expectedItem.ipAddress

            }
        }
    }
}