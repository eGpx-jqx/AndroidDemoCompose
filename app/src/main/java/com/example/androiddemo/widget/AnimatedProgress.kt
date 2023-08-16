package com.example.androiddemo.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedProgressDemo() {

    //进度值
    val progress = remember { mutableStateOf(0.1f) }

    //动画
    val animatedProgress by animateFloatAsState(
        targetValue = progress.value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "label"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
        //圆形进度条指示器
        CircularProgressIndicator(
            progress = animatedProgress //如果不设置process,则进度条会一直加载
        )
        Spacer(Modifier.requiredHeight(30.dp))
        OutlinedButton(
            onClick = { if (progress.value < 1f) progress.value += 0.1f }
        ) {
            Text(text = "增加进度")
        }
    }

}