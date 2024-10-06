package preliminary.myweatheroverview.util

import preliminary.myweatheroverview.data.remote.response.OpenWeatherDto

sealed interface WeatherState {

    data object Empty : WeatherState
    data class Loading(val isLoading: Boolean = false) : WeatherState
    data class Success(val openWeatherDto: OpenWeatherDto?) : WeatherState
    data class Error(val message: String) : WeatherState

}
