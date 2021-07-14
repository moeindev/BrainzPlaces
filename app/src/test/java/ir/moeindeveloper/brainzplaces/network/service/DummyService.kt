package ir.moeindeveloper.brainzplaces.network.service

import com.skydoves.sandwich.ApiResponse
import ir.moeindeveloper.brainzplaces.network.entity.DummyEntity
import retrofit2.http.GET

/**
 * Dummy service for the sake of the testing
 */
interface DummyService {

    @GET("DummyGateway")
    suspend fun getDummies(): ApiResponse<ArrayList<DummyEntity>>

}