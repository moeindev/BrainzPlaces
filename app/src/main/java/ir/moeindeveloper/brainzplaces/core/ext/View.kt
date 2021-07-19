package ir.moeindeveloper.brainzplaces.core.ext

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


inline fun View.isLottieAnimationView(view: (LottieAnimationView) -> Unit) {
    if (this is LottieAnimationView) {
        view(this)
    }
}

inline fun View.isTextView(view: (TextView) -> Unit) {
    if (this is TextView) {
        view(this)
    }
}


private var titleBarHeight = 0
private val dipsMap: MutableMap<Float, Int> = mutableMapOf()

internal fun Activity.convertDpToPixel(dp: Float): Int {
    if (dipsMap.containsKey(dp)) return dipsMap[dp]!!
    val resources = this.resources
    val metrics = resources.displayMetrics
    val value = (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    dipsMap[dp] = value
    return value
}

internal fun Activity.getStatusBarHeight(): Int {
    if (titleBarHeight > 0) return titleBarHeight
    val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    titleBarHeight = if (resourceId > 0) {
        this.resources.getDimensionPixelSize(resourceId)
    } else convertDpToPixel(25f)
    return titleBarHeight
}


@ExperimentalCoroutinesApi
fun TextInputEditText.textChanges(): Flow<CharSequence?> =
    callbackFlow {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                appLog { "Before text $s" }
            }

            override fun afterTextChanged(s: Editable?) {
                appLog { "After text changed" }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }

        addTextChangedListener(listener)

        awaitClose { removeTextChangedListener(listener) }
    }