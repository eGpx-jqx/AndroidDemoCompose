package com.example.androiddemo.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


//==================================layout来模拟实现paddingFromBaseLine========================
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.firstBaseLineOnTop(
    firstBaseLineOnTop: Dp
) = Modifier.layout { measurable, constraints ->
    //使用constraints布局约束对当前组件完成测量, 测量结果放在placeable中
    val placeable = measurable.measure(constraints)
    //验证组件存在FirstBaseline数据
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    //自定义父项顶部到子组件基线高度 减去 组件顶部到基线的高度 , 得到y坐标偏移高度
    val placeY = firstBaseLineOnTop.roundToPx() - placeable[FirstBaseline]
    //该组件占有的高度应该为 原组件高度 + 偏移量
    var height = placeable.height + placeY
    //完成测量,布局当前组件
    layout(placeable.width, height) {
        //该组件摆放位置
        placeable.placeRelative(0, placeY)
    }
}

//使用
@Composable
fun TextWithFirstBaseLineOnTop() {
    Text(
        text = "Hi there FirstBaseLineOnTop",
        modifier = Modifier.firstBaseLineOnTop(25.dp)
    )

    Text(
        text = "Hi there Normal Padding",
        modifier = Modifier.padding(top = 25.dp)
    )
}


//============================组件下所有子组件的自定义布局,  模拟实现Column=====================
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, contrains ->
        //对content里的每个组件进行测量,放到placeableList中
        val placeableList = measurables.map { measurable -> measurable.measure(contrains) }.toList()
        //垂直 y坐标从0开始
        var yPosition = 0
        //MyOwnColumn布局, 使用父项约束的最大宽高
        layout(contrains.maxWidth, contrains.maxHeight) {
            placeableList.forEach { placeable: Placeable ->
                //摆放组件坐标位置, 每次更新yPosition,达到垂直摆放的目的
                placeable.placeRelative(0, yPosition)
                yPosition += placeable.height
            }
        }
    }
}

//使用
@Composable
fun TextMyOwnColumn() {
    MyOwnColumn {
        Text(
            text = "Hi there FirstBaseLineOnTop",
        )
        Text(
            text = "Hi there Normal Padding",
        )
    }

}

//=======================固有特性测量intrinsic==================
/**
 * 内置固有特性测量,  只能对已经适配固有特性测量的内置组件使用IntrinsicSize. Min或IntrinsicSize. Max，否则程序运行时会crash.
 */
@Composable
fun TwoRext() {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)  //Row组件使用内部高度大小最小值()固有特性测量 ,以下是满足text的高度.
    ) {
        Text("Hi android", modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.Start))
        Divider(modifier = Modifier.width(4.dp).fillMaxHeight().background(Color.Red)) //直接使用父约束的最大高度
        Text("here", modifier = Modifier.weight(1f).padding(end = 4.dp).wrapContentWidth(Alignment.End))
    }
}
/**
 * 自定义固有特性测量
 */

