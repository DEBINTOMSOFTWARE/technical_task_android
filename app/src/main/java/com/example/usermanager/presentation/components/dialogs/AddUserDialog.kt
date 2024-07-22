package com.example.usermanager.presentation.components.dialogs

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.presentation.components.BodyText
import com.example.usermanager.presentation.components.HeaderText
import com.example.usermanager.utils.dimenResource
import kotlin.math.sin

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

    CustomDialog(
        title = title,
        headerContent = { },
        bodyContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.margin_medium)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_normal))
            ) {
                TextField(
                    value = name, onValueChange = { name = it }, label = {
                        BodySmallText(
                            text = stringResource(
                                R.string.name_label
                            ), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    })
                TextField(
                    value = email, onValueChange = { email = it }, label = {
                        BodySmallText(
                            text = stringResource(R.string.email_label), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    })
                TextField(
                    value = gender, onValueChange = { gender = it }, label = {
                        BodySmallText(
                            text = stringResource(R.string.gender_label), fontSize = dimenResource(
                                id = R.dimen.font_size_extra_small
                            ).sp
                        )
                    })
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
        onPositiveButtonClick = { onAddUser(name, email, gender, isActive) },
        onNegativeButtonClick = onDismiss,
        positiveButtonText = stringResource(R.string.add_label),
        negativeButtonText = stringResource(R.string.cancel_label)
    )
}