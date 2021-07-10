package ir.moeindeveloper.brainzplaces.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Base Service test class.
 *
 * Provides basic tools for creating and using retrofit service interface
 */
@RunWith(JUnit4::class)
abstract class ServiceTester<T> {
    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var webServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun setupWebServer() {
        webServer = MockWebServer()
        webServer.start()
    }

    @After
    @Throws(IOException::class)
    fun finishWebServer() {
        webServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(
        body: String,
        headers: Map<String, String> = emptyMap(),
        code: Int = 200
    ) {
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        webServer.enqueue(
            mockResponse
                .setBody(body)
                .setResponseCode(code)
        )
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(webServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
            .create(clazz)
    }
}