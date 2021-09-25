package com.example.androidappwireframe.model.network

import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PinCodeAPI {
    @GET("/pincode/{pin}")
    fun getData(
        @Path("pin") pin :String
    ): Single<PinCode.PinCodeData>
}