package preliminary.myweatheroverview.domain.motion

import android.hardware.SensorEventListener
import preliminary.myweatheroverview.util.motion.MovePositionsState


interface MovePositionsRepository : SensorEventListener {
    suspend fun getSensorData(
//        positionX: Float,
//        positionY: Float,
//        positionZ: Float
    ) : MovePositionsState?



    /**
        // Rotation matrix based on current readings from accelerometer and magnetometer.
        // SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading

        suspend fun getOrientationData(
            orientationX: Float,
            orientationY: Float,
            orientationZ: Float
        )
     */


    /**
     * These calls register, unregisters the SensorListener
     */
    fun manageRegisterListenerContext(): MovePositionsRepository

}