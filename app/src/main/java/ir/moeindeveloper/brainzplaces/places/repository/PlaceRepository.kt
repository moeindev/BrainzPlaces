package ir.moeindeveloper.brainzplaces.places.repository

import ir.moeindeveloper.brainzplaces.core.state.UiState
import ir.moeindeveloper.brainzplaces.places.entity.Place
import kotlinx.coroutines.flow.Flow


interface PlaceRepository {

    /**
     * Search places using
     */
    suspend fun searchPlaces(query: String): UiState<List<Place>>

}