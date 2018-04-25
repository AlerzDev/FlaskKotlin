package com.alexdev.arnduinokotlin.api

import com.alexdev.arnduinokotlin.models.SendMessageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArduinoApi{
    @GET("message/{msg}")
    fun sendMessage(@Path("msg") id: String): Call<SendMessageResponse>

    @GET("temp/{lon}/{lat}")
    fun sendtemp(@Path("lat") lat: String,@Path("lon") lon: String): Call<SendMessageResponse>

    @GET("time_date/{lon}/{lat}")
    fun sendLatLonDateTime(@Path("lat") lat: String,@Path("lon") lon: String): Call<SendMessageResponse>
}