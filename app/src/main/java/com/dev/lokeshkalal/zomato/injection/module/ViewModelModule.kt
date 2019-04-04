package com.dev.lokeshkalal.zomato.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.lokeshkalal.zomato.injection.ViewModelFactory
import com.dev.lokeshkalal.zomato.ui.homeScreen.HomeScreenViewModel
import com.dev.lokeshkalal.zomato.ui.restaurentDetail.RestaurentDetailViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    abstract fun bindHomeScreenViewModel(viewModel: HomeScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurentDetailViewModel::class)
    abstract fun bindRestaurentDetailViewModel(viewModel: RestaurentDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)