package ir.moeindeveloper.brainzplaces.core.platform.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import ir.moeindeveloper.brainzplaces.core.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Base repository class contains tools and methods that we need in a repository
 */
abstract class BaseRepository {

    /**
     * Converting [suspend] functions from services to [UiState].
     */
    @Deprecated("We don't need this in our case")
    fun <T> apiRequest(
        request: suspend () -> ApiResponse<T>
    ): Flow<UiState<T>> = flow {
        emit(UiState.loading())
        request().suspendOnSuccess {
            this.data.whatIfNotNull { data ->
                emit(UiState.success(data))
            }
        }.suspendOnException {
            /*
            This <T> should be here, I thinks it's related to this issue:
            https://github.com/Kodein-Framework/Kodein-DI/issues/306
             */
            emit(UiState.failure<T>(this.exception.localizedMessage))
        }.suspendOnError {
            emit(UiState.failure<T>(this.statusCode.toString()))
        }
    }

    /**
     * Get the exact value of the api response
     */
    suspend fun <T> takeExactValueFromTheAPI(
        request: suspend () -> ApiResponse<T>
    ): T? {
        var response: T? = null

        request().suspendOnSuccess {
            this.data.whatIfNotNull { data ->
                response = data
            }
        }.suspendOnException {
            response = null
        }.suspendOnError {
            response = null
        }

        return response
    }
}