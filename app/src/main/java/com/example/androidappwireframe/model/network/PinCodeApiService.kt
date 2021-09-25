package com.example.androidappwireframe.model.network

import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PinCodeApiService {

    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(PinCodeAPI::class.java)

    fun getData(pin:String): Single<PinCode.PinCodeData> {
        return api.getData(
          pin
        )
    }
}