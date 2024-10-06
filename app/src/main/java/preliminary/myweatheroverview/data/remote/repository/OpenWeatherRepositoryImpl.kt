package preliminary.myweatheroverview.data.remote.repository

import android.util.Log
import preliminary.myweatheroverview.data.remote.OpenWeatherApi
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import preliminary.myweatheroverview.util.WeatherState
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : OpenWeatherRepository {
    override suspend fun getBriefWeather(latitude: Double, longitude: Double
    ): WeatherState {
        val weatherState : WeatherState = try {
            WeatherState.Success(openWeatherApi.getOpenWeather(
                latitude,
                longitude,
//                API_KEY
            )
            )
        } catch (e: Exception) {
            Log.e("Error_HTTP", "${e.message}")
            WeatherState.Error("An error occurred: ${e.message}")
        }
        return weatherState
    }
}