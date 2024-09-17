package preliminary.myweatheroverview.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import preliminary.myweatherOverview.R
import preliminary.myweatheroverview.presentation.view.OpenWeatherViewModel
import preliminary.myweatheroverview.presentation.view.composable.CityTitleItem
import preliminary.myweatheroverview.presentation.view.composable.ImageItem
import preliminary.myweatheroverview.presentation.view.composable.TemperaturesItem
import preliminary.myweatheroverview.util.WeatherState


@Composable
fun WeatherScreen(modifier: Modifier) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<OpenWeatherViewModel>()
    val stateValueCollected = viewModel.state.collectAsState().value

    var latitude by remember { mutableStateOf("32.779167") }
    var longitude by rememberSaveable { mutableStateOf("-96.808891") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp)
            .background(Color(0xFFECE3CA)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 8.dp, 12.dp)
        ) {

            // To View
            item {
                var defaultColor = MaterialTheme.colorScheme.secondaryContainer
                var dominantColor by remember {
                    mutableStateOf(defaultColor)
                }
                when (stateValueCollected) {
                    is WeatherState.Loading -> "Loading..."
                    is WeatherState.Empty -> ""
                    is WeatherState.Error -> {
                        "Weather: \n\n${stateValueCollected.message}"
                    }
                    is WeatherState.Success -> {
                        stateValueCollected.openWeatherDto?.apply {
                            if (!this.main.temp.isNaN()) {
                                CityTitleItem(modifier, stateValueCollected)
                                TemperaturesItem(modifier, stateValueCollected)
                                ImageItem(stateValueCollected, context, dominantColor, modifier)
                            }
                        }
                    }
                }
            }

            // To Business
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 72.dp, 8.dp, 2.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.4f),
                        value = latitude,
                        onValueChange = { latitude = it },
                        label = { Text("Latitude") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            showKeyboardOnFocus = false)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .padding(start = 4.dp),
                        value = longitude,
                        onValueChange = { lon ->
                            longitude = lon
                        },
                        label = { Text("Longitude") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            showKeyboardOnFocus = false)
                    )
                }
            }
            item {
                Button(
                    modifier = modifier
                        .padding(4.dp, 2.dp, 8.dp, 8.dp)
                        .fillParentMaxWidth(),
                    onClick = {
                        if (latitude.isNotEmpty() && longitude.isNotEmpty())
                            viewModel.fetchWeather(latitude, longitude)
                    },
                    content = {
                        Text("Fetch Weather")
                    }
                )
            }
        }
    }

}

