package com.example.usermanager.framework.di

import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.domain.usecase.adduser.AddUser
import com.example.usermanager.domain.usecase.adduser.AddUserImpl
import com.example.usermanager.domain.usecase.deleteUser.DeleteUser
import com.example.usermanager.domain.usecase.deleteUser.DeleteUserImpl
import com.example.usermanager.domain.usecase.getusers.GetUsers
import com.example.usermanager.domain.usecase.getusers.GetUsersImpl
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

    @Provides
    @ViewModelScoped
    fun providesAddUser(usersRepository: UsersRepository): AddUser =
        AddUserImpl(usersRepository)

    @Provides
    @ViewModelScoped
    fun providesDeleteUser(usersRepository: UsersRepository): DeleteUser =
        DeleteUserImpl(usersRepository)


}