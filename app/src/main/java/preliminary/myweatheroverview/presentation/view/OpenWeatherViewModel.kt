package preliminary.myweatheroverview.presentation.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import preliminary.myweatheroverview.util.WeatherState
import javax.inject.Inject

@HiltViewModel
class OpenWeatherViewModel @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository
) : ViewModel() {

//   val repo = bindOpenWeatherRepository(repository: OpenWeatherRepositoryImpl) : OpenWeatherRepository

    private lateinit var scope: Job
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val state = _state.asStateFlow()

    fun fetchWeather(latitude: String, longitude: String) {

      scope =   viewModelScope.launch(
            // TODO: use different set of light-thread
            context = Dispatchers.IO
        ) {
            /**    %%%%   Async-task-1   %%%%   */

            withContext(
                // TODO: use different set of light-thread
                context = Dispatchers.Unconfined
            ) {
                /**  %%%%    Dummy Async-task-2:  %%%%
                 *
                 * Here it is not being used, and is a 'placeHolder' for future use:
                 * examples:
                 *  a. Accelerometer readings update, every 500ms
                 *  b. Touch-screen input when done by User
                 *
                 * */
            }

            withContext(Dispatchers.IO) {
                // Async-task-2: TODO:fetch weather-data on IO-light-thread
                when (
                    val response = openWeatherRepository.getBriefWeather(
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble()
                    )
                ) {
                    WeatherState.Empty -> WeatherState.Empty
                    is WeatherState.Loading -> {
                        _state.update { WeatherState.Loading(true) }
                    }

                    is WeatherState.Error -> {
                        _state.update { WeatherState.Error(message = "an error occurred") }
                    }

                    is WeatherState.Success -> {
                        _state.update {
                            WeatherState.Success(openWeatherDto = response.openWeatherDto)
                        }
                    }
                }
            }
        }
        
    }
    
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
    
}