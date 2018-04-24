package com.aim.atlas.util

import com.aim.atlas.api.ApiResponse
import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean


class LiveDataCallAdapter<R>() : CallAdapter<R, LiveData<ApiResponse<R>>> {
    private var responseType: Type? = null

    constructor(responseType: Type) : this() {
        this.responseType = responseType
    }

    override fun responseType(): Type? {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            internal var started = AtomicBoolean(false)
            override
            fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override
                        fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse(response))
                        }

                        override
                        fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}