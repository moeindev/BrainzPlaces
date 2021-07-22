package ir.moeindeveloper.brainzplaces.places.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LifeSpan(
    @SerializedName("begin")
    val begin: String?,
    @SerializedName("end")
    val end: String?,
    @SerializedName("ended")
    val ended: Boolean?
)