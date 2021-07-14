package ir.moeindeveloper.brainzplaces.places.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Place(
    @SerializedName("address")
    val address: String?,
    @SerializedName("area")
    val area: Area?,
    @SerializedName("coordinates")
    val coordinates: Coordinates?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("life-span")
    val lifeSpan: LifeSpan?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("score")
    val score: String?,
    @SerializedName("type")
    val type: String?
)