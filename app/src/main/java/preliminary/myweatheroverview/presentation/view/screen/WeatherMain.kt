package preliminary.myweatheroverview.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import preliminary.myweatherOverview.BuildConfig
import preliminary.myweatherOverview.R
import preliminary.myweatheroverview.data.remote.response.toTemperatureScales
import preliminary.myweatheroverview.presentation.view.OpenWeatherViewModel
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
            .background(
                Color(0xFFECE3CA)
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

            item {
                val defaultColor = MaterialTheme.colorScheme.secondaryContainer
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
                                    text = "${stateValueCollected.openWeatherDto.name} "
                                )
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

                                Row(modifier = Modifier) {
                                    //Load the Image
                                    val imageUrl =
                                        BuildConfig.ICON_URL + "wn/" + stateValueCollected.openWeatherDto.weather.first().icon + "@2x.png"
                                    val imageState: AsyncImagePainter.State =
                                        rememberAsyncImagePainter(
                                            model = ImageRequest.Builder(context)
                                                .data(imageUrl)
                                                .size(Size.ORIGINAL)
                                                .build()
                                        ).state
                                    if (imageState is AsyncImagePainter.State.Success) {
                                        dominantColor = MaterialTheme.colorScheme.secondaryContainer
                                        Image(
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .padding(6.dp)
                                                .height(150.dp)
                                                .clip(RoundedCornerShape(22.dp)),
                                            painter = imageState.painter,
                                            contentDescription = "weather_icon",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    //Load the Image Ends
                                }
                            }
                        }
                    }
                }

            }

            // For business
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
                            showKeyboardOnFocus = false
                        )
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
                            showKeyboardOnFocus = false
                        )
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