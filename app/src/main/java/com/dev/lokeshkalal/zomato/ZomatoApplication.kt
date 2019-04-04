package com.dev.lokeshkalal.zomato

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.dev.lokeshkalal.zomato.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ZomatoApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
       DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}