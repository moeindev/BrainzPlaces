package ir.moeindeveloper.brainzplaces.core.ext

import android.util.Log
import ir.moeindeveloper.brainzplaces.BuildConfig

enum class LogType {
    DEBUG,
    INFORMATION,
    ERROR,
    WTF,
    WARN,
    VERBOSE
}

/**
 * EXT function that simplifies [Log]
 */
inline fun Any.appLog(type: LogType = LogType.DEBUG, tag: String = this.javaClass.simpleName, throwable: Throwable? = null, message: () -> String) {
    when (type) {
        LogType.DEBUG -> {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message(), throwable)
            }
        }

        LogType.INFORMATION -> {
            Log.i(tag, message(), throwable)
        }

        LogType.ERROR -> {
            Log.e(tag, message(), throwable)
        }

        LogType.WTF -> {
            Log.wtf(tag, message(), throwable)
        }

        LogType.WARN -> {
            Log.w(tag, message(), throwable)
        }

        LogType.VERBOSE -> {
            Log.v(tag, message(), throwable)
        }
    }
}