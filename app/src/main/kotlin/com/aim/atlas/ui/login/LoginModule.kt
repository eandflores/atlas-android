package com.aim.atlas.ui.login

import com.aim.atlas.di.FragmentBuildersModule
import com.aim.atlas.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class LoginModule {
    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    internal abstract fun contributeLoginActivity(): LoginActivity
}
