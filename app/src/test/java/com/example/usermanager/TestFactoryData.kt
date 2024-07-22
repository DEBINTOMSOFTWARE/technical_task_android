package com.example.usermanager

import com.example.usermanager.data.model.UserItem
import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity

val userList = listOf(
    UserItem(
        email = TestConstants.EMAIL,
        gender = TestConstants.GENDER,
        id = TestConstants.ID,
        name = TestConstants.NAME,
        status = TestConstants.STATUS
    )
)

val addRequestData = AddUserRequestDataEntity(
    name = TestConstants.NAME,
    email = TestConstants.EMAIL,
    gender = TestConstants.GENDER,
    status = TestConstants.STATUS
)

val userItem = UserItem(
    email = TestConstants.EMAIL,
    gender = TestConstants.GENDER,
    id = TestConstants.ID,
    name = TestConstants.NAME,
    status = TestConstants.STATUS
)

val user = UserItemEntity(
    email = TestConstants.EMAIL,
    gender = TestConstants.GENDER,
    id = TestConstants.ID,
    name = TestConstants.NAME,
    status = TestConstants.STATUS
)

