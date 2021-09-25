package com.example.androidappwireframe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.model.network.PinCodeApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class PinCodeViewModel :ViewModel(){

    private val pinCodeApiService=PinCodeApiService()
    private val compositeDisposable= CompositeDisposable()

    val load= MutableLiveData<Boolean>()
    val response= MutableLiveData<PinCode.PinCodeData>()
    val loadError= MutableLiveData<Boolean>()

    fun getDataFromApi(pin:String){
        compositeDisposable.add(
            pinCodeApiService.getData(pin)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PinCode.PinCodeData>(){
                    override fun onSuccess(value: PinCode.PinCodeData) {
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