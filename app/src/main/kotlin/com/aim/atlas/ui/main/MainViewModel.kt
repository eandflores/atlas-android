package com.aim.atlas.ui.main

import android.arch.lifecycle.ViewModel
import android.content.Context





class MainViewModel : ViewModel() {

    private var context : Context? = null

    fun MainViewModel(context : Context) {
        this.context = context
    }

    fun init() {

    }



}