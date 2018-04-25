package com.alexdev.arnduinokotlin.activities

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alexdev.arnduinokotlin.R
import com.alexdev.arnduinokotlin.dialogs.SendMessageDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.content.Context.LOCATION_SERVICE
import com.alexdev.arnduinokotlin.api.ApiUtils
import com.alexdev.arnduinokotlin.api.ArduinoApi
import com.alexdev.arnduinokotlin.models.SendMessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity()
{

    private val INITIAL_PERMS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS)
    private val INITIAL_REQUEST = 1337
    private var locationManager : LocationManager? = null
    private var arduinoApi: ArduinoApi? = null

    companion object {
        val TAG = this::class.toString().split(".").last().dropLast(10)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arduinoApi = ApiUtils.getArduinoAPI()
        if (!canAccessLocation()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST)
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        setEventViews()
    }

    private fun setEventViews()
    {
        buttonSendMessage.setOnClickListener {
            openDialogSendMessage()
        }
        buttonGetWeather.setOnClickListener {
            sendLatLongForTemp()
        }
        buttonDateTime.setOnClickListener {
            sendLatLongForTime()
        }
    }

    private fun openDialogSendMessage()
    {
        runOnUiThread {
            val dialog = SendMessageDialog(this)
            dialog.show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendLatLongForTemp()
    {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val longitude = location.longitude
        val latitude = location.latitude
        Log.d("lst",latitude.toString())
        Log.d("long",longitude.toString())
        arduinoApi!!.sendtemp(latitude.toString(),longitude.toString()).enqueue(object : Callback<SendMessageResponse>{
            override fun onFailure(call: Call<SendMessageResponse>?, t: Throwable?) {
                Log.d(TAG,t!!.message)
            }

            override fun onResponse(call: Call<SendMessageResponse>?, response: Response<SendMessageResponse>?) {
                try
                {
                    if(response!!.body()!!.success!!)
                    {
                        Log.d(TAG,"ok")
                    }
                }
                catch (ex: Exception)
                {
                    Log.d(TAG,ex.message)
                }
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun sendLatLongForTime()
    {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val longitude = location.longitude
        val latitude = location.latitude
        Log.d("lst",latitude.toString())
        Log.d("long",longitude.toString())
        arduinoApi!!.sendLatLonDateTime(latitude.toString(),longitude.toString()).enqueue(object : Callback<SendMessageResponse>{
            override fun onFailure(call: Call<SendMessageResponse>?, t: Throwable?) {
                Log.d(TAG,t!!.message)
            }

            override fun onResponse(call: Call<SendMessageResponse>?, response: Response<SendMessageResponse>?) {
                try
                {
                    if(response!!.body()!!.success!!)
                    {
                        Log.d(TAG,"ok")
                    }
                }
                catch (ex: Exception)
                {
                    Log.d(TAG,ex.message)
                }
            }
        })
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun canAccessLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm)
    }
}
