package ir.moeindeveloper.brainzplaces.core.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.skydoves.whatif.whatIf
import com.skydoves.whatif.whatIfNotNull
import ir.moeindeveloper.brainzplaces.core.ext.isLottieAnimationView
import ir.moeindeveloper.brainzplaces.core.ext.isTextView
import ir.moeindeveloper.brainzplaces.core.state.UiState
import ir.moeindeveloper.brainzplaces.core.state.UiStatus

/**
 * Data binding adapters for later use
 */

@BindingAdapter("android:hideOnLoading")
fun View.hideOnLoading(state: UiState<*>) {
    visibility = if (state.status == UiStatus.LOADING) View.GONE else View.VISIBLE
}

@BindingAdapter("android:showOnLoading")
fun View.showOnLoading(state: UiState<*>) {
    whatIf(
        state.status == UiStatus.LOADING,
        {
            visibility = View.VISIBLE

            isLottieAnimationView { lottie ->
                if (!lottie.isAnimating) lottie.resumeAnimation()
            }
        },
        {
            visibility = View.GONE

            isLottieAnimationView { lottie ->
                if (lottie.isAnimating) lottie.pauseAnimation()
            }
        }
    )
}

@BindingAdapter("android:showOnError")
fun View.showOnError(state: UiState<*>) {
    whatIf(
        state.status == UiStatus.Failure,
        {
            visibility = View.VISIBLE

            isLottieAnimationView { lottie ->
                if (!lottie.isAnimating) lottie.resumeAnimation()
            }

            isTextView { tv ->
                state.errorMessage.whatIfNotNull { failure ->
                    tv.text = failure
                }
            }
        },
        {
            visibility = View.GONE

            isLottieAnimationView { lottie ->
                if (lottie.isAnimating) lottie.pauseAnimation()
            }
        }
    )
}

@BindingAdapter("android:hideOnError")
fun View.hideOnError(state: UiState<*>) {
    whatIf(state.status == UiStatus.Failure) {
        visibility = View.GONE

        isLottieAnimationView { lottie ->
            if (lottie.isAnimating) lottie.pauseAnimation()
        }
    }
}

@BindingAdapter("android:showOnSuccess")
fun View.showOnSuccess(state: UiState<*>) {
    whatIf(state.status == UiStatus.SUCCESS) {
        visibility = View.VISIBLE

        isLottieAnimationView { lottie ->
            if (!lottie.isAnimating) lottie.resumeAnimation()
        }
    }
}