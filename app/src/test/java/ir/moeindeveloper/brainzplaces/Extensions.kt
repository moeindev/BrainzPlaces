package ir.moeindeveloper.brainzplaces

import com.google.gson.Gson
import ir.moeindeveloper.brainzplaces.places.entity.PlacesResponse

inline fun <T> T.assertThat(block: T.() -> Unit) = block()

inline fun <T> Iterable<T>.assertForeach(block: T.(position: Int) -> Unit) =
    forEachIndexed { index, item ->
        item.run { block(index) }
    }

fun String.toPlaceResponse(): PlacesResponse? {
    val gson = Gson()

    return gson.fromJson(this, PlacesResponse::class.java)
}