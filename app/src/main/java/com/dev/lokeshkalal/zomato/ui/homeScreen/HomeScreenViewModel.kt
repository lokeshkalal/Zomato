package com.dev.lokeshkalal.zomato.ui.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.ui.state.Resource
import com.dev.lokeshkalal.zomato.ui.state.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(private val zomatoRepository: ZomatoRepository) : ViewModel() {


    val categoryLiveData: MutableLiveData<Resource<List<RestaurentCategory>>>

    val disposable: CompositeDisposable

    init {
        categoryLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }


    fun getCategories(): LiveData<Resource<List<RestaurentCategory>>> {
        return categoryLiveData
    }


    fun fetchCatgories() {
        categoryLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        disposable.add(
            zomatoRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(FetchCategoriesSubscriber())
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


    inner class FetchCategoriesSubscriber : DisposableSingleObserver<List<RestaurentCategory>>() {
        override fun onSuccess(restaurentCategoryList: List<RestaurentCategory>) {
            categoryLiveData.postValue(Resource(ResourceState.SUCCESS, restaurentCategoryList, null))
        }

        override fun onError(e: Throwable) {
            categoryLiveData.postValue(Resource(ResourceState.ERROR, null, null))
        }

    }

}
