package preliminary.myweatheroverview.presentation.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import preliminary.myweatheroverview.ui.theme.MyWeatherTheme
import preliminary.myweatheroverview.util.WeatherState


@Composable
fun CityTitleItem (
    modifier: Modifier,
    stateValueCollected: WeatherState.Success
) {
    Text(
        modifier = modifier
            .padding(top = 32.dp, end = 8.dp, bottom = 16.dp)
            .background(Color.Blue.copy(0.3f, 0.2f)),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surface,
            shadow = Shadow(Color.Black)
        ),
        text = "${stateValueCollected.openWeatherDto?.name} "
    )
}

@Preview
@Composable
fun CityTitleItemPreview() {
    MyWeatherTheme {
        CityTitleItem(Modifier.padding(16.dp), WeatherState.Success(null))
    }
}

