package com.dev.lokeshkalal.zomato.remote

import com.dev.lokeshkalal.zomato.remote.mapper.CategoryMapper
import com.dev.lokeshkalal.zomato.remote.mapper.RestaurentDetailMapper
import com.dev.lokeshkalal.zomato.remote.mapper.RestaurentListingMapper
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.remote.service.ZomatoService
import com.dev.lokeshkalal.zomato.repository.ZomatoRemote
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import io.reactivex.Single
import javax.inject.Inject

class ZomatoRemoteImpl @Inject constructor(
    private val zomatoService: ZomatoService
    , private val categoryMapper: CategoryMapper
    , private val restaurentListingMapper: RestaurentListingMapper
    , private val restaurentDetailMapper: RestaurentDetailMapper
) : ZomatoRemote {


    override fun getCategories(): Single<List<RestaurentCategory>> {
        return zomatoService.getCategories().map {
            it.categories.map { category ->
                categoryMapper.mapModelFromResponse(category.categories)
            }
        }
    }


    override fun getRestaurentList(category: Int, lat: Double, long: Double, offset: Int): Single<List<Restaurent>> {
        return zomatoService.getRestaurentList(category, lat, long, offset)
            .map {
                it.restaurants.map { restaurant ->
                    restaurentListingMapper.mapModelFromResponse(restaurant.restaurant)
                }
            }

    }

    override fun getRestaurentDetail(restaurentID: Int): Single<RestaurentDetail> {
        return zomatoService.getRestaurentDetail(restaurentID).map { restaurentDetailMapper.mapModelFromResponse(it) }

    }
}