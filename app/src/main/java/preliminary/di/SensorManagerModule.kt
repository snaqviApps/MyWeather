package preliminary.di

import android.content.Context
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SensorManagerModule @Inject constructor(
) {
    @Provides
    @Singleton
    fun providesSensorManager (
       context: Context
    ) : SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

}