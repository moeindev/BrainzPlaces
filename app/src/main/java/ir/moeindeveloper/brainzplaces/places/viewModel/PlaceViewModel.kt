package ir.moeindeveloper.brainzplaces.places.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.moeindeveloper.brainzplaces.core.ext.transformToFlow
import ir.moeindeveloper.brainzplaces.core.platform.viewModel.BaseViewModel
import ir.moeindeveloper.brainzplaces.core.state.UiState
import ir.moeindeveloper.brainzplaces.places.action.PlaceActions
import ir.moeindeveloper.brainzplaces.places.entity.Place
import ir.moeindeveloper.brainzplaces.places.repository.PlaceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(repository: PlaceRepository): BaseViewModel<PlaceActions>() {

    private val _places: MutableStateFlow<UiState<List<Place>>?> = scope {
        viewAction.transformToFlow { action ->
            if (action is PlaceActions.Search) {
                emit(UiState.loading())
                delay(1000L)
                emit(repository.searchPlaces(action.query))
            }
        }
    }

    val places: StateFlow<UiState<List<Place>>?> get() = _places
}