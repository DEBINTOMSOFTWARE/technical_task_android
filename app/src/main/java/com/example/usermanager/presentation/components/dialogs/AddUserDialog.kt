package com.example.usermanager.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.utils.dimenResource

@Composable
fun AddUserDialog(
    title: String,
    onDismiss: () -> Unit,
    onAddUser: (String, String, String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var genderError by remember { mutableStateOf<String?>(null) }

    val isFormValid by remember {
        derivedStateOf {
            name.isNotBlank() && email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) &&
                    (gender.equals("male", true) || gender.equals("female", true))
        }
    }

    UMCustomDialog(
        title = title,
        headerContent = { },
        bodyContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_normal))
            ) {
                TextField(
                    value = name, onValueChange = {
                        name = it
                        nameError = if (it.isBlank()) "Name must be at least 1 letter" else null
                    }, label = {
                        BodySmallText(
                            text = stringResource(
                                R.string.name_label
                            ), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    },
                    isError = nameError != null
                )
                if (nameError != null) {
                    BodySmallText(text = nameError ?: "", color = MaterialTheme.colorScheme.error)
                }
                TextField(
                    value = email, onValueChange = {
                        email = it
                        emailError =
                            if (!it.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) "Invalid email format" else null
                    }, label = {
                        BodySmallText(
                            text = stringResource(R.string.email_label), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    },
                    isError = emailError != null
                )
                if (emailError != null) {
                    BodySmallText(
                        text = emailError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
                TextField(
                    value = gender, onValueChange = {
                        gender = it
                        genderError = if (!(it.equals("male", true) || it.equals(
                                "female",
                                true
                            ))
                        ) "Gender must be 'male' or 'female'" else null
                    }, label = {
                        BodySmallText(
                            text = stringResource(R.string.gender_label), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    },
                    isError = genderError != null
                )
                if (genderError != null) {
                    BodySmallText(
                        text = genderError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.padding_small))
                ) {
                    BodySmallText(text = if (isActive) "Active" else "Inactive")
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_medium)))
                    Switch(
                        checked = isActive,
                        onCheckedChange = { isChecked -> isActive = isChecked },
                    )
                }
            }
        },
        onDismiss = onDismiss,
        onPositiveButtonClick = {
            if (isFormValid) {
                onAddUser(name, email, gender, isActive)
            }
        },
        onNegativeButtonClick = onDismiss,
        positiveButtonText = stringResource(R.string.add_label),
        negativeButtonText = stringResource(R.string.cancel_label)
    )
}