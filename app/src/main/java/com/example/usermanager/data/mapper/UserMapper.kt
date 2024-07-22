package com.example.usermanager.data.mapper

import com.example.usermanager.data.model.UserListItem
import com.example.usermanager.data.model.UserListResponse
import com.example.usermanager.domain.model.UserListItemEntity

fun List<UserListItem>.toDomain() : List<UserListItemEntity> {
    return map {
        UserListItemEntity(
            email = it.email,
            gender = it.gender,
            id = it.id,
            name = it.name,
            status = it.status
        )
    }
}