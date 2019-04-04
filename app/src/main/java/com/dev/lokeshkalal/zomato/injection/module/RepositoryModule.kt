package com.dev.lokeshkalal.zomato.injection.module

import com.dev.lokeshkalal.zomato.remote.ZomatoRemoteImpl
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.ZomatoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ZomatoRepositoryImpl): ZomatoRepository
}