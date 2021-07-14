package ir.moeindeveloper.brainzplaces.core.platform.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.moeindeveloper.brainzplaces.core.platform.action.BaseAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

/**
 * Base viewModel containing [BaseAction]
 */
abstract class BaseViewModel<A: BaseAction>: ViewModel() {

    val viewAction: Channel<A> = Channel()


    fun doAction(action: A) {
        viewModelScope.launch {
            viewAction.send(action)
        }
    }


    inline fun <T> scope(crossinline block: () -> Flow<T>): MutableStateFlow<T?> {
        return MutableStateFlow<T?>(null).apply {
            viewModelScope.launch {
                emitAll(block())
            }
        }
    }

}
