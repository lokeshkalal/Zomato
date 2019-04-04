package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class ZomatoEvent(
    @SerializedName("event")
    val event: Event
)