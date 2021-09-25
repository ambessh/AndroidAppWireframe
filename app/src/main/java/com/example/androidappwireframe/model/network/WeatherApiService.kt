package com.example.androidappwireframe.model.network

import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.model.entities.Weather
import com.example.androidappwireframe.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiService {

    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL_WEATHER)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getData(state:String): Single<Weather.WeatherData> {
        return api.getData(
       Constants.Weather_Api_Key,
            state,
            Constants.aqi_value

        )
    }
}