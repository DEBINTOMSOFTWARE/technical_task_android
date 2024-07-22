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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.usermanager.R
import com.example.usermanager.presentation.components.BodySmallText
import com.example.usermanager.presentation.components.HeaderText

@Composable
fun CustomDialog(
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
        title =  { HeaderText(text = title, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                headerContent()
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
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
                TextButton(onClick = onNegativeButtonClick) {
                    BodySmallText(text = negativeButtonText)
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_small)))
                TextButton(onClick = onPositiveButtonClick) {
                    BodySmallText(text = positiveButtonText)
                }
            }
        }
    )
}