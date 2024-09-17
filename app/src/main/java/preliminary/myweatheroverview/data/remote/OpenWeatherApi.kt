package preliminary.myweatheroverview.data.remote

import preliminary.myweatherOverview.BuildConfig.API_KEY
import preliminary.myweatheroverview.data.remote.response.OpenWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getOpenWeather (
        @Query("lat") latitude : Double = 33.038334,
        @Query("lon") longitude : Double = -97.006111,
        @Query("appid") appId : String = API_KEY
    ) : OpenWeatherDto
}