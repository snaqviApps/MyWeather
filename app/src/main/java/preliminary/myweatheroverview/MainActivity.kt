package preliminary.myweatheroverview

import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import preliminary.myweatheroverview.presentation.view.OpenWeatherViewModel
import preliminary.myweatheroverview.presentation.ui.theme.MyWeatherTheme
import preliminary.myweatheroverview.presentation.view.screen.CumulateDisplay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sensorManager: SensorManager
    private val viewModel: OpenWeatherViewModel by viewModels()  // Inject ViewModel here

    private var significantMotionSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyWeatherTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues ->
//                    WeatherScreen(
                    CumulateDisplay(
                        modifier = Modifier.padding()
                    )
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        println("onResume_call")

        sensorManager.registerListener(
            viewModel.registerSensorContext(),
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
//        viewModel.acquireMotionData(1.0f, 2.0f, 3.0f)
        val triggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent?) {
                // Do work
                event?.apply {
//                    Log.d("Sensor_Triggered", "Linear acceleration: x=${values[0]}, y=${values[1]}, z=${values[2]}")
//                    viewModel.acquireMotionData(values[0], values[1], values[2])  //
                }
//                viewModel.acquireMotionData()
            }
        }
        significantMotionSensor?.also { sensor ->
            sensorManager.requestTriggerSensor(triggerEventListener, sensor)
        }

//        viewModel.acquireMotionData(2.2f, 3.3f, 5.5f)
        viewModel.acquireMotionData()

    }

    override fun onPause() {
        super.onPause()
        println("onPause_call")
        sensorManager.unregisterListener(viewModel.registerSensorContext())
    }

}