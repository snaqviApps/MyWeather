package preliminary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import preliminary.myweatheroverview.data.remote.repository.OpenWeatherRepositoryImpl
import preliminary.myweatheroverview.domain.OpenWeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOpenWeatherRepository(repository: OpenWeatherRepositoryImpl): OpenWeatherRepository
}