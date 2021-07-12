package ir.moeindeveloper.brainzplaces.core.state

/**
 * UI state to communicate between UI and ViewModels
 */
sealed class UiState {
    object Loading : UiState()
    data class Failure(val message: String? = null) : UiState()
    object Success : UiState()
}