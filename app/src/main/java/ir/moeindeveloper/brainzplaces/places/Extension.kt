package ir.moeindeveloper.brainzplaces.places

import android.os.Build
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skydoves.whatif.whatIfNotNull
import ir.moeindeveloper.brainzplaces.core.ext.appLog
import ir.moeindeveloper.brainzplaces.places.entity.Place
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


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

fun Place.latLng(): LatLng? {
    return if (coordinates != null) {
        return if (coordinates.latitude != null && coordinates.longitude != null) {
            LatLng(coordinates.latitude.toDouble(), coordinates.longitude.toDouble())
        } else {
            null
        }
    } else {
        null
    }
}

fun Place.toMarkerOptions(): MarkerOptions? {
    return if (latLng() != null) {
        MarkerOptions()
            .position(
                latLng()!!
            ).title(name)
    } else {
        null
    }
}


fun Place.markerTag(): String = id ?: name ?: "nullTag"


fun List<Place>.getAverageCoordinates(): LatLng {
    var sumOfAllLat = 0.0
    var sumOfAllLng = 0.0

    forEach { place ->
        place.latLng().whatIfNotNull { ltLn ->
            sumOfAllLat += ltLn.latitude
            sumOfAllLng += ltLn.longitude
        }
    }

    return LatLng(
        sumOfAllLat/size, sumOfAllLng/size
    )
}

/**
 * filter [Place] by [markerTag]
 */
fun List<Place>.getPlaceByTag(tag: String): Place? =
    firstOrNull { place -> place.markerTag() == tag }

@ExperimentalTime
fun Place.disappearAfterLifeSpan(shouldDisappear: (Place) -> Unit) {
    appLog { "life span --> $lifeSpan" }
    val startYear = lifeSpan?.begin?.convertToYear() ?: getCurrentYear()
    val baseYear = 1990
    val delayTime = Duration.seconds(abs(startYear - baseYear)).inWholeMilliseconds

    appLog { "delay time -----> $delayTime" }
    Timer("disappear marker", false).schedule(delayTime) {
        shouldDisappear(this@disappearAfterLifeSpan)
    }
}