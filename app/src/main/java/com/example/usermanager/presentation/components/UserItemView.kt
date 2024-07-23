package com.example.usermanager.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.usermanager.R
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.utils.dimenResource

@Composable
fun UserItemView(user: UserItemEntity, onLongPress: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress()
                    }
                )
            }
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            BodySmallText(
                text = "Name: ${user.name}",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp,
                fontWeight = FontWeight.Bold
            )
            BodySmallText(
                text = "Email: ${user.email}",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp,
                fontWeight = FontWeight.Bold
            )
            BodySmallText(
                text = "Gender: ${user.gender}",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp,
                fontWeight = FontWeight.Bold
            )
            BodySmallText(
                text = "Status: ${user.status}",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}