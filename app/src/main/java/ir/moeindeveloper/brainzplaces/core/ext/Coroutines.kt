package ir.moeindeveloper.brainzplaces.core.ext

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

/**
 * This function will turn [Channel] into [Flow],
 * later we use it inside ViewModels
 */
inline fun <T, R> Channel<T>.transformToFlow(
    crossinline transform: suspend FlowCollector<R>.(value: T) -> Unit
): Flow<R> = flow {
    this@transformToFlow.consumeAsFlow().collect { value ->
        return@collect transform(value)
    }
}