package com.example.usermanager.data.network

import com.example.usermanager.data.NetworkConstants
import com.example.usermanager.data.model.UserListItem
import com.example.usermanager.data.model.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("${NetworkConstants.API_PATH}${NetworkConstants.USERS}")
    suspend fun getUsers(@Query("page") page: Int) : Response<List<UserListItem>>
}