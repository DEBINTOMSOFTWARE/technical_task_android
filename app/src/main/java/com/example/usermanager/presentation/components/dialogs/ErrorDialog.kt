package com.example.usermanager.presentation.components.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.presentation.components.BodyText
import com.example.usermanager.presentation.components.HeaderText

@Composable
fun ErrorDialog(
    title: String,
    headerText: String,
    bodyText: String,
    onDismiss: () -> Unit,
    onTryAgain: () -> Unit,
    onExit: () -> Unit,
) {
    CustomDialog(
        title = title,
        headerContent = { BodyText(text = headerText, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) },
        bodyContent = { BodySmallText(text = bodyText) },
        onDismiss = onDismiss,
        onPositiveButtonClick = onTryAgain,
        onNegativeButtonClick = onExit,
        positiveButtonText = stringResource(id = R.string.try_again_label),
        negativeButtonText = stringResource(id = R.string.exit_label)
    )
}