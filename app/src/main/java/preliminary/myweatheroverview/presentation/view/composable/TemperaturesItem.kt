package preliminary.myweatheroverview.presentation.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import preliminary.myweatheroverview.data.remote.response.toTemperatureScales
import preliminary.myweatheroverview.util.WeatherState

@Composable
fun TemperaturesItem (
    modifier: Modifier,
    stateValueCollected: WeatherState.Success
) {
    Row(
        modifier = Modifier
            .padding(top = 4.dp, end = 4.dp, bottom = 2.dp)
            .background(
                MaterialTheme.colorScheme.onSurface.copy(0.3f, 0.2f)
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f)
        ) {
            Text(
                modifier = modifier
                    .padding(bottom = 1.dp),
                fontSize = 12.sp,
                text = "Current: ${stateValueCollected.openWeatherDto.toTemperatureScales().tempF.toFloat()}°F"
            )
            Text(
                modifier = modifier
                    .padding(start = 2.dp),
                fontSize = 12.sp,
                text = "Feels: ${stateValueCollected.openWeatherDto.toTemperatureScales().feelsLike_F.toFloat()}°F"
            )
        }
        Column(
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth(0.5f)
        ) {
            Text(
                modifier = modifier.padding(start = 0.dp),
                fontSize = 12.sp,
                text = "Maximum: ${stateValueCollected.openWeatherDto.toTemperatureScales().tempF_Max.toFloat()}°F"
            )
        }
    }
}