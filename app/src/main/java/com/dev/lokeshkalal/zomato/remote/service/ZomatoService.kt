package com.dev.lokeshkalal.zomato.remote.service

import com.dev.lokeshkalal.zomato.remote.model.CategoryResponse
import com.dev.lokeshkalal.zomato.remote.model.RestaurentListingResponse
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ZomatoService {

    @Headers("user-key: c750173e8cf7e5fdc2c331cf897ee8c3")
    @GET("api/v2.1/categories")
    fun getCategories(): Single<CategoryResponse>


    @Headers("user-key: c750173e8cf7e5fdc2c331cf897ee8c3")
    @GET("api/v2.1/search")
    fun getRestaurentList(
        @Query("category") category: Int,
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("start") offset: Int
    ): Single<RestaurentListingResponse>


    @Headers("user-key: c750173e8cf7e5fdc2c331cf897ee8c3")
    @GET("api/v2.1/restaurant")
    fun getRestaurentDetail(@Query("res_id") restaurentID: Int): Single<RestaurentDetailResponse>
}