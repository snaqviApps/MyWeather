package preliminary.myweatheroverview.data.remote.response

import com.google.gson.annotations.SerializedName
import preliminary.myweatheroverview.util.TemperatureScales

data class OpenWeatherDto (
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String = "City",
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)

fun OpenWeatherDto.toTemperatureScales() : TemperatureScales {

    return TemperatureScales(
        tempC =  (main.temp - 273.15),
        tempC_Max = (main.tempMax - 273.15),
        tempC_Min = (main.tempMin - 273.15),

        tempF = (1.8 * (main.temp - 273.15) + 32),
        tempF_Max = (1.8 * (main.tempMax - 273.15) + 32.toDouble()),
        tempF_Min = (1.8 * (main.tempMin - 273.15) + 32.toDouble()),

        feelsLike_C = (main.feelsLike - 273.15),
        feelsLike_F = (1.8 * (main.feelsLike - 273.15) + 32.toDouble()))

}