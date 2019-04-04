package com.dev.lokeshkalal.zomato.repository

import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.remote.service.ZomatoService
import com.dev.lokeshkalal.zomato.remote.service.ZomatoServiceFactory
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import io.reactivex.Single
import javax.inject.Inject

class ZomatoRepositoryImpl @Inject constructor(val zomatoRemote: ZomatoRemote) : ZomatoRepository {


    override fun getRestaurents(category: Int, lat: Double, long: Double, offset: Int): Single<List<Restaurent>> {
        return zomatoRemote.getRestaurentList(category, lat, long, offset)
    }

    override fun getCategories(): Single<List<RestaurentCategory>> {
        return zomatoRemote.getCategories()
    }


    override fun getRestaurentDetail(restaurentID: Int): Single<RestaurentDetailResponse> {
        return zomatoRemote.getRestaurentDetail(restaurentID)
    }
}