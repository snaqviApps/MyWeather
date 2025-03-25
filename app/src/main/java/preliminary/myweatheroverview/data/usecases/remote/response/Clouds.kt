package preliminary.myweatheroverview.data.usecases.remote.response


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)