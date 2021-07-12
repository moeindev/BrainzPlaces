package ir.moeindeveloper.brainzplaces.core.ext

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