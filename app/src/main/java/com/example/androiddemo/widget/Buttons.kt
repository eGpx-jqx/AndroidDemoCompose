package com.example.androiddemo.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * icon组件相关
 */
@Composable
fun IconButtons() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        //icon组件
        IconButton(
            onClick = {}
        ) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "desc")
        }

        //FloatingActionButton悬浮按钮
        FloatingActionButton(
            onClick = {}
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "desc")
        }

        //带有文字扩展的FAB
        ExtendedFloatingActionButton(
            onClick = {},
            icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = "add") },
            text = { Text("add something") }
        )
    }

    /**
     * 交互式button， 点击边框变为绿色
     */
    fun interactionButton() {

        val interactionSource = remember { MutableInteractionSource() }
        val isPressedAsState = interactionSource.collectIsPressedAsState()
        val color = if (isPressedAsState.value) Color.Green else Color.White

        Button(
            onClick = {},
            interactionSource = interactionSource,
            border = BorderStroke(2.dp,color)
        ) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("确认")
        }

    }




}