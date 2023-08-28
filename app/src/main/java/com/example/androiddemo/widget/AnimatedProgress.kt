package com.example.androiddemo.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedProgressDemo() {

    //进度值
    val progress = remember { mutableFloatStateOf(0.1f) }

    //动画
    val animatedProgress by animateFloatAsState(
        targetValue = progress.floatValue,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "label"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
        //圆形进度条指示器
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp).paddingFromBaseline(top = 200.dp),
            progress = animatedProgress //如果不设置process,则进度条会一直加载
        )
        Spacer(Modifier.requiredHeight(30.dp))
        OutlinedButton(
            onClick = { if (progress.floatValue < 1f) progress.floatValue += 0.1f }
        ) {
            Text(text = "增加进度")
        }
    }

}