package com.example.androiddemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

class SearchBox {
}


@Composable
fun SearchBox(name: String) {

    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically, //垂直校准
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp).fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search"
                    )
                    Box(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "搜索一下哈~~~",
                                style = TextStyle(
                                    Color.Gray
                                )
                            )
                        }
                        it()
                    }
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = { text = "" },
                            modifier = Modifier.size(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "close"
                            )
                        }
                    }

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, CircleShape)
                .padding(horizontal = 10.dp)
                .height(30.dp)
        )
    }
}