package preliminary.myweatheroverview.presentation.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import preliminary.myweatheroverview.domain.motion.MovePositionsRepository
import preliminary.myweatheroverview.util.WeatherState
import preliminary.myweatheroverview.util.motion.MovePositionsState
import javax.inject.Inject

@HiltViewModel
class OpenWeatherViewModel @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository,
    private val moveRepository: MovePositionsRepository,           // need to move heavy-lifting here from this viewModel
) : ViewModel() {


//   val repo = bindOpenWeatherRepository(repository: OpenWeatherRepositoryImpl) : OpenWeatherRepository

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val state = _state.asStateFlow()

    private val _movePositionState = MutableStateFlow<MovePositionsState>(MovePositionsState.Empty)
    val movePositionState = _movePositionState.asStateFlow()

//    fun acquireMotionData(posX: Float, posY: Float, posZ: Float) {
    fun acquireMotionData() {
        viewModelScope.launch {
        val positions: Deferred<MovePositionsState?> = async {
                moveRepository.getSensorData()
            }  // await() gets triggered hence does not keep it deferred anymore

                // Fetch Positions
                when (val loggedPositions: MovePositionsState? = positions.await()) {
                    is MovePositionsState.Empty -> { MovePositionsState.Empty }
                    is MovePositionsState.Loading -> {
                        _movePositionState.update { MovePositionsState.Loading(true) }
                    }
                    is MovePositionsState.Error -> {
                        _movePositionState.update { MovePositionsState.Error(message = "logging error occurred") }
                    }
                    is MovePositionsState.Success -> {
                        _movePositionState.update {
                            MovePositionsState.Success(
                                loggedPositions.positions,
                                logging = { printLog(loggedPositions.positions) }
                            )
                        }
                    }

                    null -> {
                        MovePositionsState.Error(message = "Internal data-acquisition error occurred")
                    }
                }
        }

    }

    private fun printLog(positions: FloatArray) {
        println("vModel success: ${positions[0]}")
    }


    fun fetchWeather(latitude: String, longitude: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val result1 = async { println("start result1") }.await()    /** Coroutine: 1 */
            val result2 = async { println("start result2") }            /** Coroutine-deferred: 2 */

            val weather: Deferred<WeatherState> = async {
                /** Coroutine: 3 */
                println("start result3")
                openWeatherRepository.getBriefWeather(
                    latitude = latitude.toDouble(),
                    longitude = longitude.toDouble()
                )
            }
            when (val response: WeatherState = weather.await()) {
                is WeatherState.Empty -> {
                    WeatherState.Empty
                }

                is WeatherState.Loading -> {
                    _state.update { WeatherState.Loading(true) }
                }

                is WeatherState.Error -> {
                    _state.update { WeatherState.Error(message = "an error occurred") }
                }

                is WeatherState.Success -> {
                    _state.update {
                        WeatherState.Success(
                            openWeatherDto = response.openWeatherDto
                        )
                    }
                }
            }
        }
    }

    fun registerSensorContext(): MovePositionsRepository {
        return moveRepository.manageRegisterListenerContext()
    }

}