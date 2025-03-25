package preliminary.myweatheroverview.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import preliminary.myweatheroverview.presentation.view.OpenWeatherViewModel

@Composable
fun CumulateDisplay(modifier: Modifier){
    val viewModel = hiltViewModel<OpenWeatherViewModel>()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                12.dp,
                48.dp,
                12.dp,
                96.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .padding(4.dp)
                .background(Color(0xFFBCC6CE))
        ) {
            item {
                MotionScreen(modifier = modifier, viewModelInMotion = viewModel)
            }
        }
        WeatherScreen(modifier = modifier, viewModelWeather = viewModel)
    }
    
}


