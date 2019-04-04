package com.dev.lokeshkalal.zomato.injection

import android.app.Application
import com.dev.lokeshkalal.zomato.ZomatoApplication
import com.dev.lokeshkalal.zomato.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        UiModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        RemoteModule::class
    )
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: ZomatoApplication)
}