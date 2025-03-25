package preliminary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import preliminary.myweatheroverview.data.usecases.local.motion.repository.MovePositionsRepositoryImpl
import preliminary.myweatheroverview.data.usecases.remote.repository.OpenWeatherRepositoryImpl
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import preliminary.myweatheroverview.domain.motion.MovePositionsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsOpenWeatherRepository(repository: OpenWeatherRepositoryImpl): OpenWeatherRepository

    @Binds
    @Singleton
    abstract fun bindsMoveRepository(repository: MovePositionsRepositoryImpl): MovePositionsRepository
}