package ir.moeindeveloper.brainzplaces.core.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Listening to [ConnectivityManager] events using [callbackFlow] and returning the results as [Flow]
 */
@ExperimentalCoroutinesApi
fun Context.networkStateFlow(): Flow<Boolean> = callbackFlow {
    val callBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            this@callbackFlow.trySend(true).isSuccess
        }

        override fun onLost(network: Network) {
            this@callbackFlow.trySend(false).isSuccess
        }
    }

    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    manager.registerNetworkCallback(
        NetworkRequest.Builder().run {
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            build()
        },
        callBack
    )

    awaitClose {
        manager.unregisterNetworkCallback(callBack)
    }
}

@ExperimentalCoroutinesApi
val Fragment.networkStateFlow: Flow<Boolean>
    get() = requireContext().networkStateFlow()