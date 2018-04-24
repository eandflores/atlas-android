package com.aim.atlas.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import android.arch.lifecycle.ViewModel
import com.aim.atlas.ui.login.LoginViewModel
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindUserViewModel(userViewModel: LoginViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AtlasViewModelFactory): ViewModelProvider.Factory
}
