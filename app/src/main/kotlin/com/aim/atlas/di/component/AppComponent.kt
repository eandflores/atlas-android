package com.aim.atlas.di.component

import android.app.Application
import com.aim.atlas.AtlasApplication
import com.aim.atlas.di.module.AppModule
import com.aim.atlas.ui.login.LoginActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (LoginActivityModule::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(atlasApp: AtlasApplication)
}
