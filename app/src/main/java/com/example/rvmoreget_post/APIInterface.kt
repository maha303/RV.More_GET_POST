package com.example.rvmoreget_post

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {

    @GET("/test/")
    fun getUser():Call<List<UserItem>>

    @POST("/test/")
    fun addUser(@Body userItem: UserItem):Call<List<UserItem>>
}