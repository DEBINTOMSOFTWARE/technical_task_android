package com.example.usermanager.data.reposirory

import com.example.usermanager.data.mapper.toData
import com.example.usermanager.data.mapper.toDomain
import com.example.usermanager.data.network.ApiService
import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity
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
    override fun getUsers(page: Int): Flow<Resource<List<UserItemEntity>>> = flow {
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

    override fun addUser(userData: AddUserRequestDataEntity): Flow<Resource<UserItemEntity>> =
        flow {
            emit(Resource.Loading)
            try {
                val userData = userData.toData()
                val response = apiService.addUser(userData)
                if (response.isSuccessful) {
                    val responseData = response.body()?.toDomain()
                    emit(Resource.Success(responseData))
                } else {
                    emit(Resource.Error(ErrorEntity.Unknown("Add User Failed")))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(mapHttpExceptionToDomainError(e)))
            } catch (e: IOException) {
                emit(Resource.Error(ErrorEntity.Network))
            } catch (e: Exception) {
                emit(Resource.Error(ErrorEntity.Unknown(e.localizedMessage ?: "An error occurred")))
            }
        }

    override fun deleteUser(userId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.deleteUser(userId)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error(ErrorEntity.Unknown("Error deleting user")))
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