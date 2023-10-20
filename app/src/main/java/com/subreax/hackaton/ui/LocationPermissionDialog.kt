package com.subreax.hackaton.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LocationPermissionDialog(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    isPermanentlyDeclined: Boolean,
    modifier: Modifier = Modifier
) {
    if (isPermanentlyDeclined) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onSubmit) {
                    Text(text = "Разрешить")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Отмена")
                }
            },
            title = {
                Text(text = "Запрос разрешения")
            },
            text = {
                Text("Для автоматического отслеживания пробега необходим доступ к геолокации")
            },
            modifier = modifier
        )
    }
}
