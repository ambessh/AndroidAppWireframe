package com.example.androidappwireframe.util

object Constants {
    //for pincode
    const val BASE_URL="https://api.postalpincode.in"
    //for weather
    const val BASE_URL_WEATHER="https://api.weatherapi.com"
    const val Weather_Api_Key="35c9f92ac5bf4df0811144140212307"
    const val Weather_Api="key"
    const val aqi_value="no"
    const val q="q"



    fun GenderList():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("Male")
        list.add("Female")
        return list
    }

}