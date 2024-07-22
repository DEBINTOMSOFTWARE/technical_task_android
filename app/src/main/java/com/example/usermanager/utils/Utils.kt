package com.example.usermanager.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
        500 -> ErrorEntity.ServiceUnavailable
        else -> ErrorEntity.Unknown(exception.localizedMessage ?: "An error occurred")
    }
}

@Composable
fun dimenResource(id: Int): Float {
    val context = LocalContext.current
    return context.resources.getDimension(id)
}