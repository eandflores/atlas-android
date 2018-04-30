package com.aim.atlas.api

import android.arch.lifecycle.LiveData
import com.aim.atlas.api.ApiResponse
import com.aim.atlas.vo.User
import retrofit2.http.GET
import retrofit2.http.Path

interface AtlasService {
    /**
     * @GET declares an HTTP GET request
     * @Path("email") annotation on the userEmail parameter marks it as a
     * replacement for the {email} placeholder in the @GET path
     * @Path("password") annotation on the userEmail parameter marks it as a
     * replacement for the {password} placeholder in the @GET path
     */
    @GET("/users/{email}/{password}")
    fun getUser(@Path("email") email: String, @Path("password") password: String) : LiveData<ApiResponse<User>>
}