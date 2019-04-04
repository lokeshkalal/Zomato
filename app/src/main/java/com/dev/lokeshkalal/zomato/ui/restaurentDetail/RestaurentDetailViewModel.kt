package com.dev.lokeshkalal.zomato.ui.restaurentDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import com.dev.lokeshkalal.zomato.ui.state.Resource
import com.dev.lokeshkalal.zomato.ui.state.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestaurentDetailViewModel @Inject constructor(private val zomatoRepository: ZomatoRepository) : ViewModel() {


    val restaurentDetailLiveData: MutableLiveData<Resource<RestaurentDetail>>

    val disposable: CompositeDisposable

    init {
        restaurentDetailLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }


    fun getRestaurentDetail(): MutableLiveData<Resource<RestaurentDetail>> {
        return restaurentDetailLiveData
    }


    fun fetchRestaurentDetail(restaurentID: Int) {
        restaurentDetailLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        disposable.add(
            zomatoRepository.getRestaurentDetail(restaurentID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(FetchRestaurentDetailSubscriber())
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


    inner class FetchRestaurentDetailSubscriber : DisposableSingleObserver<RestaurentDetail>() {
        override fun onSuccess(restaurentDetail: RestaurentDetail) {
            restaurentDetailLiveData.postValue(Resource(ResourceState.SUCCESS, restaurentDetail, null))

        }

        override fun onError(e: Throwable) {
            restaurentDetailLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))

        }

    }

}
