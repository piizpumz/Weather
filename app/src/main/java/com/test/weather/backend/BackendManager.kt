package com.test.weather.backend

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class BackendManager(url: String) {
    private val ourInstance = BackendManager(url)
    private lateinit var service: MyAPI


    fun BackendManager(url : String) {
        val retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(MyAPI::class.java)
    }

    fun getService(): MyAPI? {
        return service
    }

}


interface MyAPI {

    @GET("/data/2.5/weather")
    fun getCurrentWeather(@Query("q") q: String, @Query("appid") appId: String): Call<JsonObject>

    @GET("/data/2.5/forecast/daily")
    fun getForecastWeather(@Query("q") q: String, @Query("appid") appId: String): Call<JsonObject>


}