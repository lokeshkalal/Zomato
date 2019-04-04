package com.dev.lokeshkalal.zomato.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(private val zomatoRepository: ZomatoRepository) : ViewModel() {


    val categoryLiveData: MutableLiveData<List<RestaurentCategory>>

    val disposable: CompositeDisposable

    init {
        categoryLiveData = MutableLiveData()
        disposable = CompositeDisposable()
    }


    fun getCategories(): LiveData<List<RestaurentCategory>> {
        return categoryLiveData
    }


    fun fetchCatgories() {
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
        override fun onSuccess(t: List<RestaurentCategory>) {
            categoryLiveData.postValue(t)
        }

        override fun onError(e: Throwable) {

        }

    }

}
