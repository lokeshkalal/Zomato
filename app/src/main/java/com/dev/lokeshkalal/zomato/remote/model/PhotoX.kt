package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class PhotoX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("md5sum")
    val md5sum: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("photo_id")
    val photoId: Int,
    @SerializedName("thumb_url")
    val thumbUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("uuid")
    val uuid: Long
)