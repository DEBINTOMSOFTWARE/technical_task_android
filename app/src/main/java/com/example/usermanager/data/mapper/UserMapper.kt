package com.example.usermanager.data.mapper

import com.example.usermanager.data.model.AddUserRequestData
import com.example.usermanager.data.model.UserItem
import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity

fun List<UserItem>.toDomain() : List<UserItemEntity> {
    return map {
        UserItemEntity(
            email = it.email,
            gender = it.gender,
            id = it.id,
            name = it.name,
            status = it.status
        )
    }
}

fun AddUserRequestDataEntity.toData() : AddUserRequestData {
    return AddUserRequestData(
        name = name,
        email = email,
        gender = gender,
        status = status
    )
}

fun UserItem.toDomain() : UserItemEntity {
    return UserItemEntity(
        email = email,
        gender = gender,
        id = id,
        name = name,
        status = status
    )
}
