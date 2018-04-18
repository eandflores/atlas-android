package com.aim.atlas.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import com.aim.atlas.domain.User
import com.aim.atlas.domain.UserDao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.Executor
import javax.inject.Inject


interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{userEmail}/{userPassword}")
    fun getUser(@Path("user") userEmail: String, userPassword: String) : Call<User>
}

class UserRepository {
    private var webservice: Webservice? = null
    private var userDao: UserDao? = null
    // simple in memory cache, details omitted for brevity
    //private var userCache: UserCache? = null
    private var executor: Executor? = null


    @Inject
    fun UserRepository(webservice: Webservice, userDao: UserDao, executor: Executor) {
        this.webservice = webservice
        this.userDao = userDao
        this.executor = executor
    }

    fun getUser(userEmail: String, userPassword: String) : LiveData<User>? {
        /*val cached = userCache.get(userEmail, userPassword)
        if (cached != null) {
            return cached
        }*/

        val data = MutableLiveData<User>()
        //userCache.put(userEmail, data)
        //userCache.put(userPassword, data)

        // this is still suboptimal but better than before.
        // a complete implementation must also handle the error cases.

        refreshUser(userEmail, userPassword)
        return userDao?.load(userId = userEmail)
    }

    private fun refreshUser(userEmail: String, userPassword: String) {
        executor?.execute {
            // running in a background thread
            // check if user was fetched recently
            //val userExists = userDao?.hasUser(FRESH_TIMEOUT)
            val userExists = true
            if (!userExists) {
                // refresh the data
                val response = webservice?.getUser(userEmail, userPassword)?.execute()
                // TODO check for error etc.
                // Update the database.The LiveData will automatically refresh so
                // we don't need to do anything else here besides updating the database
                userDao?.save(response?.body()!!)
            }
        }
    }
}