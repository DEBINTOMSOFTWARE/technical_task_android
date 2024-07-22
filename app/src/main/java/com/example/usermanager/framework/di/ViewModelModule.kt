package com.example.usermanager.framework.di

import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.domain.usecase.GetUsers
import com.example.usermanager.domain.usecase.GetUsersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesGetUsers(usersRepository: UsersRepository): GetUsers =
        GetUsersImpl(usersRepository)

}