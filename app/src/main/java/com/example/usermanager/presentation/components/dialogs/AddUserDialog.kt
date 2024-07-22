package com.example.usermanager.presentation.components.dialogs

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.presentation.components.HeaderText

@Composable
fun AddUserDialog(
    title: String,
    onDismiss: () -> Unit,
    onAddUser: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    CustomDialog(
        title = title,
        headerContent = { HeaderText(text = stringResource(R.string.add_new_user_label)) },
        bodyContent = {
            Column {
               TextField(value = name, onValueChange = { name = it }, label = { BodySmallText(text = stringResource(
                   R.string.name_label
               )
               )})
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
                TextField(value = email, onValueChange = { email = it }, label = { BodySmallText(
                    text = stringResource(R.string.email_label)
                )})
            }
        },
        onDismiss = onDismiss,
        onPositiveButtonClick = { onAddUser(name, email) },
        onNegativeButtonClick = onDismiss,
        positiveButtonText = stringResource(R.string.add_label),
        negativeButtonText = stringResource(R.string.cancel_label)
    )
}