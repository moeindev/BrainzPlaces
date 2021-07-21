package ir.moeindeveloper.brainzplaces.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skydoves.sandwich.ApiResponse
import io.kotest.matchers.shouldBe
import ir.moeindeveloper.brainzplaces.MainCoroutinesRule
import ir.moeindeveloper.brainzplaces.mocks.PlacesApiResponse
import ir.moeindeveloper.brainzplaces.places.repository.PlaceRepository
import ir.moeindeveloper.brainzplaces.places.repository.PlaceRepositoryImpl
import ir.moeindeveloper.brainzplaces.places.service.PlaceService
import ir.moeindeveloper.brainzplaces.toPlaceResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class PlaceRepositoryTest {

    private val service: PlaceService = mock(PlaceService::class.java)
    private lateinit var repository: PlaceRepository

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun initialSetup() {
        repository = PlaceRepositoryImpl(service)
    }

    //Currently this is failing but I don't have time to make a JSON mock :)
    @Test
    fun `Big data response`() = runBlocking {
        val queryResult = ApiResponse.Success(response = Response.success(PlacesApiResponse.longRange.toPlaceResponse()))

        val expectedCount = 52

        whenever(service.searchPlaces(
            "ok"
        )).thenReturn(queryResult)

        runQueryToTest("ok",expectedCount)
    }

    //Same as above :)
    @Test
    fun `Average data response`() = runBlocking {
        val queryResult = ApiResponse.Success(response = Response.success(PlacesApiResponse.averageRange.toPlaceResponse()))

        val expectedCount = 28

        whenever(service.searchPlaces(
            "god"
        )).thenReturn(queryResult)

        runQueryToTest("god",expectedCount)
    }

    @Test
    fun `small data response`() = runBlocking {
        val queryResult = ApiResponse.Success(response = Response.success(PlacesApiResponse.smallRange.toPlaceResponse()))

        val expectedCount = 7

        whenever(service.searchPlaces(
            "iran"
        )).thenReturn(queryResult)

        runQueryToTest("iran",expectedCount)
    }


    private fun runQueryToTest(query: String, count: Int) = runBlocking {
        val places = repository.searchPlaces(query)
        places.data?.size shouldBe count
    }
}