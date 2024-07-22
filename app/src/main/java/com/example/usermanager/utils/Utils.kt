package com.example.usermanager.utils

import retrofit2.HttpException

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T?): Resource<T>()
    data class Error(val message: ErrorEntity): Resource<Nothing>()
}

sealed class ErrorEntity {
    data object Network : ErrorEntity()
    data object NotFound : ErrorEntity()
    data object AccessDenied : ErrorEntity()
    data object ServiceUnavailable : ErrorEntity()
    data class Unknown(val error: String) : ErrorEntity()
}

fun mapHttpExceptionToDomainError(exception: HttpException): ErrorEntity {
    return when (exception.code()) {
        404 -> ErrorEntity.NotFound
        403 -> ErrorEntity.AccessDenied
        503 -> ErrorEntity.ServiceUnavailable
        else -> ErrorEntity.Unknown(exception.localizedMessage ?: "An error occurred")
    }
}