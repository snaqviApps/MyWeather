package preliminary.myweatheroverview.domain

import preliminary.myweatheroverview.util.WeatherState

interface OpenWeatherRepository {

    suspend fun getBriefWeather (
        latitude: Double,
        longitude: Double
    ): WeatherState
}