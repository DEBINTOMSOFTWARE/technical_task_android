package com.example.usermanager.data.mapper

import com.example.usermanager.data.model.UserListResponse
import com.example.usermanager.domain.model.UserListItemEntity

fun UserListResponse.toDomain() : List<UserListItemEntity> {
    return userList.map {
        UserListItemEntity(
            email = it.email,
            gender = it.gender,
            id = it.id,
            name = it.name,
            status = it.status
        )
    }
}