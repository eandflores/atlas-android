package com.aim.atlas.ui.main

import android.arch.lifecycle.ViewModel


class MainViewModel : ViewModel() {

    private var showSplash = true
    val splashActivity = 1

    fun getSplash() : Boolean {
        return showSplash
    }

    fun setShowSplash(showSplash: Boolean) {
        this.showSplash = showSplash
    }

}