package com.example.usermanager.presentation.components.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText

@Composable
fun UMAlertDialog(
    title: String,
    headerText: String,
    bodyText: String,
    onDismiss: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    positiveButtonText: String = stringResource(id = R.string.try_again_label),
    negativeButtonText: String = stringResource(id = R.string.exit_label)
) {
    UMCustomDialog(
        title = title,
        headerContent = { BodySmallText(text = headerText, color = MaterialTheme.colorScheme.error) },
        bodyContent = { BodySmallText(text = bodyText, color = MaterialTheme.colorScheme.onPrimary) },
        onDismiss = onDismiss,
        onPositiveButtonClick = onPositiveButtonClick,
        onNegativeButtonClick = onNegativeButtonClick,
        positiveButtonText = positiveButtonText,
        negativeButtonText = negativeButtonText
    )
}