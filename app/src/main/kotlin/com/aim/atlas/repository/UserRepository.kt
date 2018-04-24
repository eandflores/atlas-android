package com.aim.atlas.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import com.aim.atlas.api.ApiResponse
import com.aim.atlas.api.AtlasService
import com.aim.atlas.db.UserDao
import com.aim.atlas.vo.User
import java.util.concurrent.Executor
import javax.inject.Inject
import com.aim.atlas.vo.Resource


class UserRepository() {
    private var webservice: AtlasService? = null
    private var userDao: UserDao? = null
    // simple in memory cache, details omitted for brevity
    //private var userCache: UserCache? = null
    private var executor: Executor? = null


    @Inject
    constructor(webservice: AtlasService, userDao: UserDao, executor: Executor) : this() {
        this.webservice = webservice
        this.userDao = userDao
        this.executor = executor
    }

    fun loadUser(login: String) : LiveData<Resource<User>>? {
        /*val cached = userCache.get(userEmail, userPassword)
        if (cached != null) {
            return cached
        }*/

        val data = MutableLiveData<User>()
        //userCache.put(userEmail, data)
        //userCache.put(userPassword, data)

        // this is still suboptimal but better than before.
        // a complete implementation must also handle the error cases.

        return object : NetworkBoundResource<User, User>() {
            override fun saveCallResult(item: User) {
                userDao?.insert(item)
            }

            override fun shouldFetch(data: User?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao!!.findByLogin(login)
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return webservice!!.getUser(login)
            }
        }.getAsLiveData()
    }

}