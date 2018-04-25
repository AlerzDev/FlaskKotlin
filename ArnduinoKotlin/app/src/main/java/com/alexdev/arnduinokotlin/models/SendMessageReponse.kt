package com.alexdev.arnduinokotlin.models


import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
        @SerializedName("success") val success: Boolean?
)