package com.example.androiddemo.sideeffect

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.RuntimeException

/**
 * 用于展示一些常用的sideEffectApi
 */


//================================DisposableEffect==============================
@Composable
fun backPressedDemo(enable: Boolean = true, onClick: () -> Unit) {

    //获取当前返回调度器
    val pressedDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "未提供返回按钮!"
    }.onBackPressedDispatcher

    //使用remember包裹,避免界面重组时被重复创建
    val callBack = remember {
        object : OnBackPressedCallback(enable) {
            override fun handleOnBackPressed() {
                onClick()
            }
        }
    }

    //当pressedDispatcher返回按钮调度器变更时,这里会重建
    DisposableEffect(pressedDispatcher) {
        //调度器添加上返回按钮执行逻辑
        pressedDispatcher.addCallback(callBack)

        //这里做DisposableEffect移除前的一些操作, 如释放资源, 此方法必须有!
        onDispose {
            callBack.remove()
        }
    }
}


//=================================SideEffect===========================
@Composable
fun SideEffectTest() {
    SideEffect {
        Log.d("SideEffect", "composable重组成功!")
    }

    Text("hello")
    throw RuntimeException("主动抛出异常, 导致重组失败 SideEffect执行不到")
}


//=====================================LaunchedEffect=========================
@Composable
fun MyScreen(state: MutableState<Boolean>, snackState: SnackbarHostState) {

    //当state错误时, snackBar展示
    //当state正确时,snackBar会小时, LaunchedEffect会自行进入onDispose,协程取消
    if (!state.value) {
        //挂起程序来展示snackBar
        //当snackState变更时,此携程任务结束,并重建
        //SnackBar的显示需要使用协程环境,LaunchedEffect提供协程环境
        LaunchedEffect(snackState) {
            snackState.showSnackbar("err")
        }
    }
}


//==================================rememberCoroutineScope=====================
@Composable
fun RememberCoroutineScopeDemo(snackBarState: SnackbarHostState) {
    //创建一个绑定RememberCoroutineScopeDemo生命周期的协程作用域
    val scoop = rememberCoroutineScope()

    Column {
        Button(
            onClick = { //onClick是 ()->Unit的方法, 非composable,  这里使用rememberCoroutineScope提供提供协程环境
                scoop.launch {
                    snackBarState.showSnackbar("click it!")
                }
            }
        ) {
            Text("Button")
        }
    }
}

/**
 * 用rememberCoroutineScope和DisposableEffect实现 LaunchedEffect
 * 以下等价于 LaunchedEffect(key){run job}
 */
fun RememberCoroutineScopeDemo1(key: Any?) {
    val scoop = rememberCoroutineScope()
    DisposableEffect(key) {
        // run job

        onDispose {
            //释放资源 job.cancel
        }
    }
}

//===================================rememberUpdatedState======================
@Composable
fun RememberUpdatedStateDemo(onTimeOut: () -> Unit) {
    //remember + mutableState  则此数据在重组期间从缓存拿到
    val currentOnTimeOut by rememberUpdatedState(onTimeOut)
    //此副作用生命周期同RememberUpdatedStateDemo一样
    //传参Unit, 不会随重组而重新执行
    LaunchedEffect(Unit) {
        delay(2000)
        currentOnTimeOut() //用了rememberUpdatedState, 此处总是能获取到最新的数据
    }
}


//==============================snapshotFlow====================
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun snapshotFlowDemo() {
    val pagerState = rememberPagerState() { 1 }

    LaunchedEffect(Unit) {
        //snapshotFlow将状态转为Coroutine Flow .   通过 快照 系统订阅状态变化
        snapshotFlow { pagerState.currentPage }.collect {
            // 一但pagerState.currentPage发生变更,这里立刻会收集到数据并执行代码流程
        }
    }
}

//=====================================produceState================================
@Composable
fun loadNetImage(url: String, imageRepository: (String) -> ByteArray?): State<ByteArray> {
    //将外部数据转为状态, 当url或imageRepository变更时会导致重新计算value值
    val defaule = ByteArray(0)
    return produceState(defaule, url, imageRepository) {
        value = imageRepository(url) ?: defaule
    }
}









//====================================derivedStateOf=======================================
@Composable
fun DerivedStateOfDemo() {
    val postList = remember { mutableStateListOf<String>()  }
    val keyWord by remember { mutableStateOf("") }

    //对postList过滤, 结果整合为一个state
    val result by remember { derivedStateOf { postList.filter { it.contains(keyWord,false) } } }
}