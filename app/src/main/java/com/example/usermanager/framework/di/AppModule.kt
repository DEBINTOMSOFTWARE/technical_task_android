package com.example.usermanager.framework.di

import android.content.Context
import com.example.usermanager.BuildConfig
import com.example.usermanager.data.NetworkConstants
import com.example.usermanager.data.network.ApiService
import com.example.usermanager.data.reposirory.UsersRepositoryImpl
import com.example.usermanager.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add("gorest.co.in", "sha256/GDUHKeXqy2tvj9f8IMm2jnvPunq4S9ffwApYicpJ8/M=")
            .build()

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    @Named("onlineInterceptor")
    fun provideOnlineInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    @Named("apiKeyInterceptor")
    fun provideAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val modifiedRequest =
            if (originalRequest.method == "POST" || originalRequest.method == "DELETE") {
                originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.USERS_KEY}")
                    .build()
            } else {
                originalRequest
            }
        chain.proceed(modifiedRequest)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        certificatePinner: CertificatePinner,
        @Named("onlineInterceptor") onlineInterceptor: Interceptor,
        @Named("apiKeyInterceptor") apiKeyInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .certificatePinner(certificatePinner)
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
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