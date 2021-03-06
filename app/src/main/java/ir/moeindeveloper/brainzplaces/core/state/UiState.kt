package ir.moeindeveloper.brainzplaces.core.state


enum class UiStatus {
    LOADING,
    SUCCESS,
    Failure
}

/**
 * UI state to communicate between UI and ViewModels
 */
data class UiState<out T>(val status: UiStatus,
                          val data: T? = null,
                          val errorMessage: String? = null) {
    companion object {
        fun <T> loading() : UiState<T> {
            return UiState(status = UiStatus.LOADING)
        }

        fun <T> failure(message: String? = null) : UiState<T> {
            return UiState(status = UiStatus.Failure, errorMessage = message)
        }

        fun <T> success(data: T?) : UiState<T> {
            return UiState(status = UiStatus.SUCCESS, data = data)
        }
    }
}