package com.example.usermanager.framework.di

import com.example.usermanager.data.NetworkConstants
import com.example.usermanager.data.network.ApiService
import com.example.usermanager.data.reposirory.UsersRepositoryImpl
import com.example.usermanager.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
          Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesUsersRepository(apiService: ApiService): UsersRepository =
        UsersRepositoryImpl(apiService)

}