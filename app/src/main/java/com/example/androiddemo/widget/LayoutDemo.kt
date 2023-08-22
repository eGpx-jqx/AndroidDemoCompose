package com.example.androiddemo.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.androiddemo.R


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
        Column(
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = null)
                }
            }
        }
    }
}


/**
 * 图片介绍
 */
@Composable
fun ArticleCardDemoPeople() {

    Surface(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.height(100.dp).width(300.dp),
        shadowElevation = 12.dp
    ) {
        Row(
            Modifier.clickable { }
        ) {
            Icon(
                painter = painterResource(R.drawable.sp),
                contentDescription = null,
                modifier = Modifier.size(100.dp).scale(0.8f)
            )
            Spacer(Modifier.padding(horizontal = 12.dp))
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("ZXC", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.padding(vertical = 8.dp))
                Text("周星驰")
            }
        }
    }
}

/**
 * ConstraintLayout 约束布局    有 Barrier（分割线，依赖于其他约束） GuidLine（引导线，独立） Chain（链约束）
 * 非约束布局是不可以创建引用和关联引用
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstraintLayoutDemo() {

    Column {
        //normal
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ) {
            //创建引用（引用只能在ConstraintLayoutScope中才能创建）   createRefs 创建最多16个引用， createRef 创建单个引用
            val (text, btn) = createRefs()

            Text(
                text = "Center Text",
                modifier = Modifier.constrainAs(text) { //将引用和Text此组件联系起来， 下面设置其约束关联 上下左右都和父布局关联最终居中显示
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
            TextButton(
                onClick = {},
                modifier = Modifier.padding(vertical = 10.dp).constrainAs(btn) {
                    start.linkTo(text.start)
                    end.linkTo(text.end)
                    top.linkTo(text.top)
                }
            ) {
                Text(text = "Center Text Button")
            }
        }

        Spacer(Modifier.height(10.dp)) //留白


        //====================================================
        //GuideLine
        ConstraintLayout(modifier = Modifier.fillMaxWidth().height(100.dp)) {

            //创建中间的一个引导线（无实体，只是辅助用）
            val guidelineFromTop = createGuidelineFromTop(0.5f)
            val (text, text1) = createRefs()
            Text(
                text = "Center Text on GuidelineFromTop",
                modifier = Modifier.constrainAs(text) { //将引用和Text此组件联系起来， 下面设置其约束关联 在引导线之上
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(guidelineFromTop)
                }
            )
            Text(
                text = "Center Text down GuidelineFromTop",
                modifier = Modifier.constrainAs(text1) { //将引用和Text此组件联系起来， 下面设置其约束关联 在引导线之下
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(guidelineFromTop)
                }
            )
        }

        Spacer(Modifier.height(10.dp)) //留白


        //====================================================
        //Barrier屏障线  如 两行 用户名，密码   为了后面对齐， 可设置竖向分割线对其最长的字段
        ConstraintLayout(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            val (userNameRef, passwdRef, userNameInRef, passwdInRef) = createRefs()
            //创建一个分割线， 以较长字段对齐
            var verticalBarrier = createEndBarrier(userNameRef, passwdRef)
            val centerGuidLine = createGuidelineFromTop(0.5f)
            Text(
                text = "用户名: ",
                modifier = Modifier.constrainAs(userNameRef) {
                    start.linkTo(parent.start)
                    bottom.linkTo(centerGuidLine,10.dp)
                }
            )
            Text(
                text = "密码: ",
                modifier = Modifier.constrainAs(passwdRef) {
                    start.linkTo(parent.start)
                    top.linkTo(centerGuidLine,10.dp)
                }
            )
            OutlinedTextField(
                value = "  ",
                onValueChange = {},
                modifier = Modifier.constrainAs(userNameInRef) {
                    start.linkTo(verticalBarrier, 10.dp)
                    top.linkTo(userNameRef.top)
                    bottom.linkTo(centerGuidLine,10.dp) //放在引导线下上面，10dp
                    height = Dimension.fillToConstraints
                }
            )
            OutlinedTextField(
                value = "  ",
                onValueChange = {},
                modifier = Modifier.constrainAs(passwdInRef) {
                    start.linkTo(verticalBarrier, 10.dp)
                    top.linkTo(centerGuidLine,10.dp) //放在引导线下面，10dp
                    bottom.linkTo(passwdRef.bottom)
                    height = Dimension.fillToConstraints
                }
            )

        }

        Spacer(Modifier.height(10.dp)) //留白


        //====================================================
        //chain链
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().height(150.dp)
        ) {
            val (start,center,end) = createRefs()
            //设置水平Chain
            createHorizontalChain(start,center,end, chainStyle = ChainStyle.Packed)
            Text(text = "start", modifier = Modifier.padding(horizontal = 5.dp).constrainAs(start){
                top.linkTo(parent.top)
            })
            Text(text = "center", modifier = Modifier.padding(horizontal = 5.dp).constrainAs(center){
                top.linkTo(parent.top)
            })
            Text(text = "end", modifier = Modifier.padding(horizontal = 5.dp).constrainAs(end){
                top.linkTo(parent.top)
            })
        }
    }

}

/**
 * compose脚手架示例
 */
fun scaffoldDemo() {

    Scaffold(
        topBar = {},
        bottomBar = {}
    ) {
        it.calculateBottomPadding()
    }
}

