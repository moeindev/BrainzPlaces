package ir.moeindeveloper.brainzplaces.places.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Coordinates(
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?
)