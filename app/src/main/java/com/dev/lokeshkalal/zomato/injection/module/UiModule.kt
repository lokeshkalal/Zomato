package com.dev.lokeshkalal.zomato.injection.module

import com.dev.lokeshkalal.zomato.ui.homeScreen.HomeScreenFragment
import com.dev.lokeshkalal.zomato.ui.restaurentDetail.RestaurentDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {


    @ContributesAndroidInjector
    abstract fun contributeHomeScreenFragment(): HomeScreenFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): RestaurentDetailFragment
}