package preliminary.myweatheroverview.data.usecases.remote

import preliminary.myweatherOverview.BuildConfig.API_KEY
import preliminary.myweatheroverview.data.usecases.remote.response.OpenWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getOpenWeather (
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("appid") appId : String = API_KEY
    ) : OpenWeatherDto
}