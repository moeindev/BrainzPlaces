package ir.moeindeveloper.brainzplaces.places.repository

import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.skydoves.whatif.whatIfNotNull
import ir.moeindeveloper.brainzplaces.core.platform.repository.BaseRepository
import ir.moeindeveloper.brainzplaces.core.state.UiState
import ir.moeindeveloper.brainzplaces.places.entity.Place
import ir.moeindeveloper.brainzplaces.places.service.PlaceService
import kotlinx.coroutines.delay

class PlaceRepositoryImpl(private val service: PlaceService): PlaceRepository, BaseRepository() {

    override suspend fun searchPlaces(query: String): UiState<List<Place>> {
        var offset = 0
        val limit = 20
        var remainingCount = 0
        var count: Int

        val collectedPlaces  = mutableListOf<Place>()
        var response: UiState<List<Place>>

        service.searchPlaces(query,offset).
            onSuccess {
                this.data.whatIfNotNull { dat ->
                    collectedPlaces.addAll(dat.places)
                    count = dat.count
                    remainingCount = count - limit
                    offset += limit
                }
            }.onError {
            response = UiState.failure<List<Place>>("Failed to make the initial state")
        }

        for (i in remainingCount downTo 0 step limit) {
            delay(2000L)
            service.searchPlaces(
                query = query,
                offset = offset,
                limit =  if (remainingCount > limit) limit else remainingCount)
                .onSuccess {
                    this.data.whatIfNotNull { dat ->
                        collectedPlaces.addAll(dat.places)
                    }
                    remainingCount -= limit
                    offset += limit
                }
        }
        response = UiState.success(data = collectedPlaces)
        return response
    }
}