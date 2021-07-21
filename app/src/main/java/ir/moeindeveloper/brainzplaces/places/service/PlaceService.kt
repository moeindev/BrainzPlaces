package ir.moeindeveloper.brainzplaces.places.service

import com.skydoves.sandwich.ApiResponse
import ir.moeindeveloper.brainzplaces.places.entity.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API service to call the search api
 */
interface PlaceService {

    /**
     * Search places using the parameters
     *
     * @param[query] Gets string query, separate the query parameters with **AND**
     *
     * @param[offset] Return search results starting at a given offset. Used for paging through more than one page of results. initial will be **0**
     *
     * @param[format] Format of the returned data, default is **XML** but we set the default to **JSON**
     *
     * @param[limit] Number of items returned per request, server defaults is **25**, our default will be **20**
     *
     * @return [ApiResponse]
     */
    @GET("place/")
    suspend fun searchPlaces(
        @Query("query") query: String,
        @Query("offset") offset: Int = 0,
        @Query("fmt") format: String = "json",
        @Query("limit") limit: Int = 20,
    ): ApiResponse<PlacesResponse>

}