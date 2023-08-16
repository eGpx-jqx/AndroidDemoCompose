package com.example.androiddemo.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AlertDialogDemo() {

    val dialogState = remember { mutableStateOf(true) }

    IconButton(
        onClick = { dialogState.value = !dialogState.value },
    ) {
        Row {
            Icon(imageVector = Icons.Filled.Call, contentDescription = null)
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text(text = "弹窗")
        }

    }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            title = { Text(text = "AlertDialog") },
            text = { Text(text = "这是一个提醒弹窗") },
            confirmButton = {
                TextButton(
                    onClick = { dialogState.value = false }
                ) {
                    Text(text = "确认")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { dialogState.value = false }
                ) {
                    Text(text = "取消")
                }
            },
        )
    }
}