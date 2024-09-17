package preliminary.myweatheroverview.data.remote

import preliminary.myweatherOverview.BuildConfig.API_KEY
import preliminary.myweatheroverview.data.remote.response.OpenWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

//    latitude = 47.218102, longitude = -1.552800))
    @GET("weather")
    suspend fun getOpenWeather (
        @Query("lat") latitude : Double = 33.038334,
        @Query("lon") longitude : Double = -97.006111,
        @Query("appid") appId : String = API_KEY
    ) : OpenWeatherDto
}

// https://api.openweathermap.org/data/2.5/weather?lat=47.218102&lon=-1.552800&appid=322dfe0053266222e709e2f1015aaab0