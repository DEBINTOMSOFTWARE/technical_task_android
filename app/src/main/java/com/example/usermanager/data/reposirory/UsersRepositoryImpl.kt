package com.example.usermanager.data.reposirory

import android.util.Log
import com.example.usermanager.data.mapper.toDomain
import com.example.usermanager.data.network.ApiService
import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import com.example.usermanager.utils.mapHttpExceptionToDomainError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    UsersRepository {
    override fun getUsers(page: Int): Flow<Resource<List<UserListItemEntity>>> = flow {
            emit(Resource.Loading)
        try {
            val initialResponse = apiService.getUsers(page)
            if (initialResponse.isSuccessful) {
                val totalPages = initialResponse.headers()["x-pagination-pages"]?.toInt()
                if (totalPages != null) {
                    val finalResponse = apiService.getUsers(totalPages)
                    if (finalResponse.isSuccessful) {
                        val users = finalResponse.body()?.toDomain()
                        if (users != null) {
                            emit(Resource.Success(users))
                        } else {
                            emit(Resource.Error(ErrorEntity.Unknown("Error Fetching Users")))
                        }
                    } else {
                        emit(Resource.Error(ErrorEntity.Unknown("Error Fetching Users")))
                    }
                } else {
                    emit(Resource.Error(ErrorEntity.Unknown("Pagination headers not found")))
                }
            } else {
                emit(Resource.Error(ErrorEntity.Unknown("Error Fetching Users")))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(mapHttpExceptionToDomainError(e)))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorEntity.Network))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorEntity.Unknown(e.localizedMessage ?: "An error occurred")))
        }
    }
}