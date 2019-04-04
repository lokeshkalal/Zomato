package com.dev.lokeshkalal.zomato.ui.restaurents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.ui.state.Resource
import com.dev.lokeshkalal.zomato.ui.state.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RestaurentListingViewModel constructor(private val zomatoRepository: ZomatoRepository) : ViewModel() {


    val restaurentLiveData: MutableLiveData<Resource<List<Restaurent>>>

    val disposable: CompositeDisposable
    var moreDataAvailable: Boolean

    init {
        restaurentLiveData = MutableLiveData()
        disposable = CompositeDisposable()
        moreDataAvailable = true
    }

    fun getRestaurents(): LiveData<Resource<List<Restaurent>>> {
        return restaurentLiveData
    }

    fun fetchRestaurents(category: Int, lat: Double, long: Double, offset: Int) {
        if (offset > 0) {
            restaurentLiveData.postValue(Resource(ResourceState.PAGINATING, restaurentLiveData.value?.data, null))
        } else {
            restaurentLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        }

        disposable.add(
            zomatoRepository.getRestaurents(category, lat, long, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(FetchRestaurentSubscriber())
        )
    }

    fun shouldFetchMoreData(): Boolean {
        return (restaurentLiveData.value?.resourceState != ResourceState.PAGINATING) && (restaurentLiveData.value?.resourceState != ResourceState.LOADING) && moreDataAvailable
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    inner class FetchRestaurentSubscriber : DisposableSingleObserver<List<Restaurent>>() {
        override fun onSuccess(restaurentList: List<Restaurent>) {
            moreDataAvailable = !restaurentList.isEmpty()
            val currentList = obtainCurrentData().toMutableList()
            currentList.addAll(restaurentList)
            restaurentLiveData.postValue(Resource(ResourceState.SUCCESS, currentList, null))
        }

        override fun onError(e: Throwable) {
            restaurentLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        }

    }

    private fun obtainCurrentData() = restaurentLiveData.value?.data ?: emptyList()
}