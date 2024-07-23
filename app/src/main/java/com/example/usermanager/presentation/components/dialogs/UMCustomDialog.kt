package com.example.usermanager.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.presentation.components.BodyText

@Composable
fun UMCustomDialog(
    title: String,
    headerContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    positiveButtonText: String,
    negativeButtonText: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            BodyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp).semantics {
                        contentDescription = title
                    },
                text = title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                headerContent()
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_normal)))
                bodyContent()
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onNegativeButtonClick, modifier = Modifier.semantics {
                    role = Role.Button
                    contentDescription = negativeButtonText
                }) {
                    BodySmallText(text = negativeButtonText)
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_small)))
                TextButton(onClick = onPositiveButtonClick, modifier = Modifier.semantics {
                    role = Role.Button
                    contentDescription = positiveButtonText
                }) {
                    BodySmallText(text = positiveButtonText)
                }
            }
        }
    )
}