package com.dev.lokeshkalal.zomato.injection.module

import com.dev.lokeshkalal.zomato.BuildConfig
import com.dev.lokeshkalal.zomato.remote.ZomatoRemoteImpl
import com.dev.lokeshkalal.zomato.remote.service.ZomatoService
import com.dev.lokeshkalal.zomato.remote.service.ZomatoServiceFactory
import com.dev.lokeshkalal.zomato.repository.ZomatoRemote
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideZomatoService(): ZomatoService {
            return ZomatoServiceFactory.makeZomatoService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(zomatoRemote: ZomatoRemoteImpl): ZomatoRemote
}