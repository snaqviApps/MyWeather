package preliminary.myweatheroverview.data.usecases.local.motion.repository

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import preliminary.myweatheroverview.domain.motion.MovePositionsRepository
import preliminary.myweatheroverview.util.motion.MovePositionsState
import javax.inject.Inject

class MovePositionsRepositoryImpl @Inject constructor (
    private val sensorManager: SensorManager
) : MovePositionsRepository {
    private val alpha: Float = 0.8f
    private val gravity = FloatArray(3)
    private val linearAcceleration = FloatArray(3)

    override suspend fun getSensorData(
//        positionX: Float,
//        positionY: Float,
//        positionZ: Float
    ): MovePositionsState? {
      val movePositionsState: MovePositionsState? = try {
          MovePositionsState.Success(                       /** Fetch sensor data */
              linearAcceleration
          ) { println("MovePositions in ${this::class.qualifiedName}") }
      } catch (e: IllegalStateException) {
            Log.e("Error_Sensor_reading", "${e.message}")
          MovePositionsState.Error("An error occurred: ${e.message}")
        }
        return movePositionsState
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {

//          if(sensorEvent.sensor.highestDirectReportRateLevel)
//        if(sensorEvent.sensor.isWakeUpSensor) {
            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2]

            // Remove the gravity contribution with the high-pass filter.
            linearAcceleration[0] = sensorEvent.values[0]    // - gravity[0]
            linearAcceleration[1] = sensorEvent.values[1]    // - gravity[1]
            linearAcceleration[2] = sensorEvent.values[2]    // - gravity[2] ------> would show graving when facing up i.e: 9.871,/S^2
//        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        when(sensor?.type){
            1 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: Accelerometer")
            }
            2 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: MAGNETIC_FIELD")
            }
            3 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: ORIENTATION")
            }
            4 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: TYPE_GYROSCOPE = 4")
            }
            5 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: TYPE_LIGHT = 5")
            }
            6 -> {
                Log.d("Sensor_Accuracy", "Sensor-type: TYPE_PRESSURE = 6")
            }
        }

    }

    override fun manageRegisterListenerContext() = this

}