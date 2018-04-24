package com.aim.atlas

/**
 * Created by edgardo on 4/20/18.
 */


import android.app.Activity
import android.app.Application
import com.aim.atlas.di.applyAutoInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class AtlasApplication : Application(), HasActivityInjector {

    @Inject
    internal var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null


    override fun onCreate() {
        super.onCreate()

        applyAutoInjector()
    }

    override
    fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}