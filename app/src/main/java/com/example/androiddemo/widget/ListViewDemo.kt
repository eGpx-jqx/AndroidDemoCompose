package com.example.androiddemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * 懒加载列表
 */
@Composable
fun LazyListViewDemo() {

    LazyColumn (Modifier.fillMaxSize().background(Color.Gray)) {
        //多个类似于items的方法
        items(50) {
            ListItem(
                headlineContent = { Text("One line list item with 24x24 icon : $it") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                    )
                },
                modifier = Modifier.padding(8.dp).background(Color.Cyan)
            )
        }
    }
}