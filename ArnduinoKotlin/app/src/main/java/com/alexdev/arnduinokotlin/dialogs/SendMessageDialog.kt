package com.alexdev.arnduinokotlin.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.alexdev.arnduinokotlin.R
import com.alexdev.arnduinokotlin.api.ApiUtils
import com.alexdev.arnduinokotlin.api.ArduinoApi
import com.alexdev.arnduinokotlin.models.SendMessageResponse
import kotlinx.android.synthetic.main.dialog_send_message.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendMessageDialog(context: Context?) : Dialog(context)
{



    private var arduinoApi: ArduinoApi? = null
    companion object {
        val TAG = this::class.toString().split(".").last().dropLast(10)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_send_message)
        arduinoApi = ApiUtils.getArduinoAPI()
        setEventViews()
    }

    private fun setEventViews()
    {
        buttonClose.setOnClickListener {
            dismiss()
        }

        buttonSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage()
    {
        if(textEditMessage.text.isNullOrBlank())
        {
            textEditMessage.setError("Enter message",null)
            return
        }

        arduinoApi!!.sendMessage(textEditMessage.text.toString()).enqueue(object : Callback<SendMessageResponse>
        {
            override fun onFailure(call: Call<SendMessageResponse>?, t: Throwable?)
            {
                Log.d(TAG,t!!.message)
            }

            override fun onResponse(call: Call<SendMessageResponse>?, response: Response<SendMessageResponse>?)
            {
                try
                {
                    if(response!!.body()!!.success!!)
                    {
                        Log.d(TAG,"ok")
                        dismiss()
                    }
                }
                catch (ex: Exception)
                {
                    Log.d(TAG,ex.message)
                }
            }
        })


    }

}




