package com.example.androidappwireframe.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.androidappwireframe.R
import com.example.androidappwireframe.databinding.ActivityWeatherTodayBinding
import com.example.androidappwireframe.model.entities.Weather
import com.example.androidappwireframe.viewmodel.WeatherViewModel
import kotlin.math.log
import kotlin.math.roundToInt

class WeatherTodayActivity : AppCompatActivity() {

    lateinit var mBinding:ActivityWeatherTodayBinding
    lateinit var weatherViewModel:WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding= ActivityWeatherTodayBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportActionBar?.setTitle("Weather today")

        weatherViewModel=ViewModelProvider(this@WeatherTodayActivity).get(WeatherViewModel::class.java)

        val intent=intent
     val state=intent.getStringExtra("state")
        mBinding.etWeather.setText(state)

        mBinding.buttonWeather.setOnClickListener{
            if(state!=null) {
                callWeatherApi(state)
            }
        }




    }

    fun callWeatherApi(state:String){
        if(weatherViewModel!=null) {
            weatherViewModel.getDataFromApi(state)
        }

        callApi()
    }

    fun callApi(){
        weatherViewModel.response.observe(this,{
            response->
            Log.i("Wea",response.toString())
            populateToUI(response)
        })
        weatherViewModel.load.observe(this,{
                response->
            Log.i("Wea",response.toString())
        })
        weatherViewModel.loadError.observe(this,{
                response->
            Log.i("Wea",response.toString())
        })
    }

    fun populateToUI(data:Weather.WeatherData){
     data.let {
         val latlon=it.location
         val temp=it.current

         mBinding.cen.text=temp.temp_c.roundToInt().toString()
         mBinding.feh.text=temp.temp_f.roundToInt().toString()
         mBinding.lat.text=latlon.lat.roundToInt().toString()
         mBinding.lon.text=latlon.lon.roundToInt().toString()
     }
    }
}