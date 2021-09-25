package com.example.androidappwireframe.model.network

import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.model.entities.Weather
import com.example.androidappwireframe.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/v1/current.json")
    fun getData(
        @Query(Constants.Weather_Api) api:String,
        @Query(Constants.q) q:String,
        @Query(Constants.q) aqi:String
    ): Single<Weather.WeatherData>
}