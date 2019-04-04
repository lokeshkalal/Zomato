package com.dev.lokeshkalal.zomato.ui.restaurents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RestaurentListingViewModel : ViewModel() {
    val zomatoRepository: ZomatoRepository

    val categoryLiveData: MutableLiveData<List<Restaurent>>

    val disposable: CompositeDisposable

    init {
        zomatoRepository = ZomatoRepository()
        categoryLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }

    fun getRestaurents(): LiveData<List<Restaurent>> {
        return categoryLiveData
    }

    fun fetchRestaurents(category: Int, lat: Double, long: Double, offset: Int) {
        disposable.add(
            zomatoRepository.getRestaurents(category, lat, long, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(FetchRestaurentSubscriber())
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    inner class FetchRestaurentSubscriber : DisposableSingleObserver<List<Restaurent>>() {
        override fun onSuccess(t: List<Restaurent>) {
            categoryLiveData.postValue(t)
        }

        override fun onError(e: Throwable) {

        }

    }
}