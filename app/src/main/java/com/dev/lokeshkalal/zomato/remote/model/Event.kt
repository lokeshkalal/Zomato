package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("book_link")
    val bookLink: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("disclaimer")
    val disclaimer: String,
    @SerializedName("display_date")
    val displayDate: String,
    @SerializedName("display_time")
    val displayTime: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("event_category")
    val eventCategory: Int,
    @SerializedName("event_category_name")
    val eventCategoryName: String,
    @SerializedName("event_id")
    val eventId: Int,
    @SerializedName("friendly_end_date")
    val friendlyEndDate: String,
    @SerializedName("friendly_start_date")
    val friendlyStartDate: String,
    @SerializedName("friendly_timing_str")
    val friendlyTimingStr: String,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("is_end_time_set")
    val isEndTimeSet: Int,
    @SerializedName("is_valid")
    val isValid: Int,
    @SerializedName("photos")
    val photos: List<Photo>,
    @SerializedName("restaurants")
    val restaurants: List<Any>,
    @SerializedName("share_data")
    val shareData: ShareData,
    @SerializedName("share_url")
    val shareUrl: String,
    @SerializedName("show_share_url")
    val showShareUrl: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("types")
    val types: List<Any>
)