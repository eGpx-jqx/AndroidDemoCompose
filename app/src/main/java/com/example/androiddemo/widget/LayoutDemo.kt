package com.example.androiddemo.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * 文章卡片 column,row,surface
 */
@Composable
fun ArticleCardDemo() {

    Surface(
        shape = RoundedCornerShape(12.dp), //圆角
        modifier = Modifier.padding(12.dp)//外边距
            .fillMaxWidth(),
        shadowElevation = 10.dp //表面以下阴影的大小
    ) {
        Column (
            Modifier.padding(12.dp)
        ) {
            Text(
                text = "Jetpack Compose介绍",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.padding(vertical = 5.dp))
            Text(
                text = "Jetpack Compose 是推荐用于构建原生Android 界面的新工具包。 它可简化并加快Android 上的界面开发，使用更少的代码、强大的工具和直观的Kotlin API，快速打造生动而精彩的应用。"
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Favorite,contentDescription = null)
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Star,contentDescription = null)
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Share,contentDescription = null)
                }
            }
        }
    }
}