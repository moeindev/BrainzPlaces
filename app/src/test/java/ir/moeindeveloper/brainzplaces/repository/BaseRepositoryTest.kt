package ir.moeindeveloper.brainzplaces.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import io.kotest.matchers.shouldBe
import ir.moeindeveloper.brainzplaces.MainCoroutinesRule
import ir.moeindeveloper.brainzplaces.core.platform.repository.BaseRepository
import ir.moeindeveloper.brainzplaces.core.state.UiState
import ir.moeindeveloper.brainzplaces.mocks.MockResponses
import ir.moeindeveloper.brainzplaces.network.entity.DummyEntity
import ir.moeindeveloper.brainzplaces.network.service.DummyService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalTime
class BaseRepositoryTest {

    private val service: DummyService = mock()
    private lateinit var repository: BaseRepository
    private val loadingType = UiState.loading<ArrayList<DummyEntity>>()

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = object : BaseRepository() { }
    }


    @Test
    fun `Network response success`() = runBlocking {
        val queryResult = ApiResponse.Success<ArrayList<DummyEntity>>(response = Response.success(MockResponses.dummyResponse))

        val expectedResult = UiState.success(MockResponses.dummyResponse)

        whenever(service.getDummies()).thenReturn(queryResult)

        runQueryToTest(expectedResult)
    }

    @Test
    fun `Network response exception`() = runBlocking {
        val queryResult = ApiResponse.error<ArrayList<DummyEntity>>(IOException("Dummy exception"))

        val expectedResult = UiState.failure<ArrayList<DummyEntity>>("Dummy exception")

        whenever(service.getDummies()).thenReturn(queryResult)

        runQueryToTest(expectedResult)
    }


    @Test
    fun `Network response Server error`() = runBlocking {
        val queryResult = ApiResponse.Failure.Error<ArrayList<DummyEntity>>(Response.error(403,"Forbidden".toResponseBody()))

        val expectedResult = UiState.failure<ArrayList<DummyEntity>>("Forbidden")

        whenever(service.getDummies()).thenReturn(queryResult)

        runQueryToTest(expectedResult)
    }

    private fun runQueryToTest( expectedResult: UiState<*>) = runBlocking {
        repository.apiRequest {
            service.getDummies()
        }.test {
            expectItem() shouldBe loadingType
            expectItem() shouldBe expectedResult
            expectComplete()
        }
    }
}