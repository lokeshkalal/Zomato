package com.dev.lokeshkalal.zomato.ui.restaurents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.ZomatoRepositoryImpl
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.ui.state.Resource
import com.dev.lokeshkalal.zomato.ui.state.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RestaurentListingViewModel constructor(private val zomatoRepository: ZomatoRepository) : ViewModel() {


    val categoryLiveData: MutableLiveData<Resource<List<Restaurent>>>

    val disposable: CompositeDisposable
    var moreDataAvailable: Boolean

    init {
        categoryLiveData = MutableLiveData()
        disposable = CompositeDisposable()
        moreDataAvailable = true
    }

    fun getRestaurents(): LiveData<Resource<List<Restaurent>>> {
        return categoryLiveData
    }

    fun fetchRestaurents(category: Int, lat: Double, long: Double, offset: Int) {
        if (offset > 0) {
            categoryLiveData.postValue(Resource(ResourceState.PAGINATING, categoryLiveData.value?.data, null))
        } else {
            categoryLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        }

        disposable.add(
            zomatoRepository.getRestaurents(category, lat, long, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(FetchRestaurentSubscriber())
        )
    }

    fun shouldFetchMoreData(): Boolean {
        return (categoryLiveData.value?.resourceState != ResourceState.PAGINATING) && (categoryLiveData.value?.resourceState != ResourceState.LOADING) && moreDataAvailable
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    inner class FetchRestaurentSubscriber : DisposableSingleObserver<List<Restaurent>>() {
        override fun onSuccess(t: List<Restaurent>) {
            moreDataAvailable = !t.isEmpty()
            val currentList = obtainCurrentData().toMutableList()
            currentList.addAll(t)
            categoryLiveData.postValue(Resource(ResourceState.SUCCESS, currentList, null))
        }

        override fun onError(e: Throwable) {
            categoryLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        }

    }

    private fun obtainCurrentData() = categoryLiveData.value?.data ?: emptyList()
}