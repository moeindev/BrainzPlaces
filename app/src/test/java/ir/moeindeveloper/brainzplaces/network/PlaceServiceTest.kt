package ir.moeindeveloper.brainzplaces.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.whatif.whatIfNotNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ir.moeindeveloper.brainzplaces.MainCoroutinesRule
import ir.moeindeveloper.brainzplaces.mocks.PlacesApiResponse
import ir.moeindeveloper.brainzplaces.places.entity.PlacesResponse
import ir.moeindeveloper.brainzplaces.places.service.PlaceService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.kotlin.mock
import java.io.IOException

class PlaceServiceTest: ServiceTester<PlaceService>() {
    private lateinit var service: PlaceService

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(PlaceService::class.java)
    }

    @Test
    @Throws(IOException::class)
    fun `Search request test`(): Unit = runBlocking {
        enqueueResponse(PlacesApiResponse.searchResponse)

        val expectedResult = mock<PlacesResponse>()

        val actualResult = service.searchPlaces("iran")

        webServer.takeRequest()

        (actualResult as ApiResponse.Success).data.whatIfNotNull { result ->
            result.created shouldNotBe null
            result.count shouldBe expectedResult.count
            result.places shouldBe result.places
        }
    }
}