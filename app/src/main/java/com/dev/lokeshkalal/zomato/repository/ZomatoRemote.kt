package com.dev.lokeshkalal.zomato.repository

import com.dev.lokeshkalal.zomato.remote.model.CategoryResponse
import com.dev.lokeshkalal.zomato.remote.model.RestaurentListingResponse
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ZomatoRemote {


    fun getCategories(): Single<List<RestaurentCategory>>

    fun getRestaurentList(
        category: Int,
        lat: Double,
        long: Double,
        offset: Int
    ): Single<List<Restaurent>>


    fun getRestaurentDetail(restaurentID: Int): Single<RestaurentDetail>
}