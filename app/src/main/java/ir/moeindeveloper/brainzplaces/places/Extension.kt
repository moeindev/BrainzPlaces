package ir.moeindeveloper.brainzplaces.places

import android.os.Build
import java.time.LocalDate
import java.util.*


fun String.convertToYear(): Int? {
    return when {
        this.length == 4 -> {
            this.toInt()
        }
        this.length < 4 -> {
            null
        }
        else -> {
            this.split('-').first().toInt()
        }
    }
}


fun getCurrentYear(): Int {
    return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
        LocalDate.now().year
    } else {
        Date().year
    }
}

fun String.buildQuery(): String = "$this ANDbegin:\"1990-01-01\""