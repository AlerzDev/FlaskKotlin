package com.alexdev.arnduinokotlin.api

import com.alexdev.arnduinokotlin.utils.Constants

class ApiUtils {
    companion object
    {
        fun getArduinoAPI(): ArduinoApi {
            return RetrofitClient.getClient(Constants.URL_BASE_API)!!.create(ArduinoApi::class.java)
        }
    }
}

