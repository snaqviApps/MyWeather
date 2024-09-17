package preliminary.myweatheroverview.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import preliminary.myweatheroverview.util.WeatherState
import javax.inject.Inject

@HiltViewModel
class OpenWeatherViewModel @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val state = _state.asStateFlow()

    fun fetchWeather(latitude: String, longitude: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = openWeatherRepository
            .getBriefWeather(
                latitude = latitude.toDouble(),
                longitude = longitude.toDouble())) {
            WeatherState.Empty -> WeatherState.Empty
            is WeatherState.Loading -> {
                _state.update { WeatherState.Loading(true) }
            }
            is WeatherState.Error -> {
                _state.update { WeatherState.Error(message = "an error occurred") }
            }
            is WeatherState.Success -> {
                _state.update { WeatherState.Success(openWeatherDto = response.openWeatherDto) }
            }
        }
    }
}