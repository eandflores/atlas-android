package com.aim.atlas.vo

import com.aim.atlas.vo.Status.ERROR
import com.aim.atlas.vo.Status.LOADING
import com.aim.atlas.vo.Status.SUCCESS

//a generic class that describes a data with a status
class Resource<T>() {

    var status : Status? = null
    var data: T? = null
    var message: String? = null

    constructor(status : Status, data: T?, message: String?) : this() {
        this.status = status
        this.data = data
        this.message = message
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}