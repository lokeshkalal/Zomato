package com.dev.lokeshkalal.zomato.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RestaurentDetailViewModel : ViewModel() {
    val zomatoRepository: ZomatoRepository

    val restaurentDetailLiveData: MutableLiveData<RestaurentDetailResponse>

    val disposable: CompositeDisposable

    init {
        zomatoRepository = ZomatoRepository()
        restaurentDetailLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }


    fun getRestaurentDetail(): MutableLiveData<RestaurentDetailResponse> {
        return restaurentDetailLiveData
    }


    fun fetchRestaurentDetail(restaurentID: Int) {
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


    inner class FetchRestaurentDetailSubscriber : DisposableSingleObserver<RestaurentDetailResponse>() {
        override fun onSuccess(t: RestaurentDetailResponse) {
            restaurentDetailLiveData.postValue(t)
        }

        override fun onError(e: Throwable) {

        }

    }

}
