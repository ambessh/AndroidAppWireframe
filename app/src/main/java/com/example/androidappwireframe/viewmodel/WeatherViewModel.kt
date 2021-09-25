package com.example.androidappwireframe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.model.entities.Weather
import com.example.androidappwireframe.model.network.PinCodeApiService
import com.example.androidappwireframe.model.network.WeatherApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel :ViewModel(){


    private val weatherApiService= WeatherApiService()
    private val compositeDisposable= CompositeDisposable()

    val load= MutableLiveData<Boolean>()
    val response= MutableLiveData<Weather.WeatherData>()
    val loadError= MutableLiveData<Boolean>()

    fun getDataFromApi(state:String){
        compositeDisposable.add(
            weatherApiService.getData(state)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Weather.WeatherData>(){
                    override fun onSuccess(value: Weather.WeatherData) {
                        load.value=true
                        response.value=value
                        loadError.value=false
                    }

                    override fun onError(e: Throwable) {
                        load.value=false
                        loadError.value=true
                        e.printStackTrace()
                    }

                })
        )
    }


}