package ir.moeindeveloper.brainzplaces.places.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlacesResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("created")
    val created: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("places")
    val places: List<Place>
)