package preliminary.myweatheroverview.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import preliminary.myweatheroverview.presentation.view.OpenWeatherViewModel
import preliminary.myweatheroverview.util.motion.MovePositionsState

@Composable
fun MotionScreen(
    viewModelInMotion: OpenWeatherViewModel,
    modifier: Modifier
) {
    val stateValueCollectedInMotion = viewModelInMotion.movePositionState.collectAsState().value
    Box(
        modifier = modifier
           .fillMaxWidth(0.75f)
           .height(144.dp)
           .background(Color(0xA09C9BC2))
         .padding(bottom = 4.dp),
    )
    {
        when(stateValueCollectedInMotion){
            is MovePositionsState.Loading -> "Loading..."
            is MovePositionsState.Empty -> ""
            is MovePositionsState.Error -> "Error: ${stateValueCollectedInMotion.message}"
            is MovePositionsState.Success -> {
                Text(
                    modifier = modifier
                        .padding(top = 32.dp, end = 8.dp, bottom = 16.dp),
//                        .background(Color.Blue.copy(0.5f, 0.2f))
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red,
                        letterSpacing = 1.sp,
                        shadow = Shadow(Color.Black, blurRadius = 8.5f)
                    ),
                    text = "Accelerations:\n\n" +
                            " X: ${stateValueCollectedInMotion.positions[0]}\n" +
                            " Y: ${stateValueCollectedInMotion.positions[1]}\n" +
                            " Z: ${stateValueCollectedInMotion.positions[2]}"
                )
            }
        }
    }


}

@Composable
fun MotionScreenPreview() {

}