package com.example.usermanager.data.network

import com.example.usermanager.data.NetworkConstants
import com.example.usermanager.data.model.AddUserRequestData
import com.example.usermanager.data.model.UserItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("${NetworkConstants.API_PATH}${NetworkConstants.USERS}")
    suspend fun getUsers(@Query("page") page: Int): Response<List<UserItem>>

    @POST("${NetworkConstants.API_PATH}${NetworkConstants.USERS}")
    suspend fun addUser(@Body userData: AddUserRequestData): Response<UserItem>

    @DELETE("${NetworkConstants.API_PATH}${NetworkConstants.USERS}/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}