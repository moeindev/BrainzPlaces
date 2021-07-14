package ir.moeindeveloper.brainzplaces.core.ext

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView


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