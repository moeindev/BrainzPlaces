package ir.moeindeveloper.brainzplaces.places.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Area(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sort-name")
    val sortName: String?
)