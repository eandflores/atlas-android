package com.aim.atlas.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Singleton


@Singleton
class AtlasViewModelFactory()  : ViewModelProvider.Factory {
    private var creators: Map<Class<out ViewModel>, ViewModel>? = null

    constructor(creators : Map<Class<out ViewModel>, ViewModel>) : this() {
        this.creators = creators
    }

    @SuppressWarnings("unchecked")
    override
    fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: ViewModel? = creators?.get(modelClass)
        if (creator == null) {
            for ((key, value) in this.creators!!) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}