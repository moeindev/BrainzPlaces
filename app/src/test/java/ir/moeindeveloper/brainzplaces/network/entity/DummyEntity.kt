package ir.moeindeveloper.brainzplaces.network.entity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DummyEntity(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("last_name")
    val lastName: String
)