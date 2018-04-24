package com.aim.atlas.api

import android.arch.lifecycle.LiveData
import com.aim.atlas.api.ApiResponse
import com.aim.atlas.vo.User
import retrofit2.http.GET
import retrofit2.http.Path

interface AtlasService {
    /**
     * @GET declares an HTTP GET request
     * @Path("userEmail") annotation on the userEmail parameter marks it as a
     * replacement for the {userEmail} placeholder in the @GET path
     * @Path("userPassword") annotation on the userEmail parameter marks it as a
     * replacement for the {userPassword} placeholder in the @GET path
     */
    @GET("/users/{userEmail}/{userPassword}")
    fun getUser(@Path("user") login: String) : LiveData<ApiResponse<User>>
}