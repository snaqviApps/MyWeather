package preliminary.myweatheroverview.presentation.view.composable

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import preliminary.myweatherOverview.BuildConfig
import preliminary.myweatheroverview.util.WeatherState

@Composable
fun ImageItem(
    stateValueCollected: WeatherState.Success,
    context: Context,
    dominantColor: Color,
    modifier: Modifier
) {
    var dominantColor1 = dominantColor
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
            dominantColor1 = MaterialTheme.colorScheme.secondaryContainer
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