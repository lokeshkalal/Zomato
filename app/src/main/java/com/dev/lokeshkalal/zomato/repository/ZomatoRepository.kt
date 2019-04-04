package com.dev.lokeshkalal.zomato.repository

import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import io.reactivex.Single

interface ZomatoRepository {

    fun getRestaurents(category: Int, lat: Double, long: Double, offset: Int): Single<List<Restaurent>>


    fun getCategories(): Single<List<RestaurentCategory>>


    fun getRestaurentDetail(restaurentID: Int): Single<RestaurentDetail>
}