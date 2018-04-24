package com.aim.atlas.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.arch.lifecycle.MediatorLiveData
import android.os.AsyncTask
import com.aim.atlas.api.ApiResponse
import com.aim.atlas.vo.Resource


// ResultType: Type for the Resource data
// RequestType: Type for the API response
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected fun onFetchFailed() {
    }

    @MainThread
    constructor() {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource, { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource, {
                    result.setValue(Resource.success(it!!))
                })
            }
        })
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource, { newData ->
            result.setValue(Resource.loading(newData))
        })
        result.addSource(apiResponse, { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response!!.isSuccessful()) {
                saveResultAndReInit(response)
            } else {
                onFetchFailed()
                result.addSource(dbSource, { newData ->
                    result.setValue(Resource.error(response.errorMessage!!, newData))
                })
            }
        })
    }



    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<RequestType>) {
        class async(response: ApiResponse<RequestType>) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                saveCallResult(response.body!!)
                return null
            }

            override fun onPostExecute(aVoid: Void) {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                result.addSource(loadFromDb(), {
                    result.setValue(Resource.success(it!!))
                })
            }
        }
        async(response).execute()
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    fun getAsLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}