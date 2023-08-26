package com.example.androiddemo.status

import android.content.res.Resources
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * Stateless: 意味着 无状态composable， 内部未持有状态
 * Stateful：意味着 有状态composable，  内部持有状态
 * State<out T>: 描述状态的可观察对象， 当Composable对State的value进行读取的同时会与State建立订阅关系，当value发生变化时，作为监听者的Composable会自动重组刷新UI。
 * （一般用MutableState<T> 内部值可变的状态），  一般使用by语法 如 var xx by mutableStateOd()
 * remember: 仅使用State当值变更会触发重组, 但重组期间状态内数据还是被初始化为默认值.  加上remember则会将此数据缓存起来,重组时使用被缓存的数据.    但remember缓存的数据不能跨越进程或Activity, 如ConfigurationChanged属性变更(切换横屏等)会导致缓存丢失.
 * rememberSavable:
 *
 */

/**
 * 自定义Saver来实现自定义类保存到Bundle,实现缓存的跨进程,Activity等存储恢复
 */
data class Person(var name: String, var age: Int)

//自定义类型
object CustomSaver : Saver<Person, Bundle> {
    override fun restore(value: Bundle): Person? {
        return value.getString("name")?.let { Person(it, value.getInt("age")) }
    }

    override fun SaverScope.save(value: Person): Bundle {
        return Bundle().apply {
            putString("name", value.name)
            putInt("age", value.age)
        }
    }
}

//mapSaver类型
val CustomSaver1 = mapSaver<Person>(
    save = { mapOf("name" to it.name, "age" to it.age) },
    restore = { Person(it["name"] as String, it["age"] as Int) }
)

//listSaver类型
val CustomSaver2 = listSaver(
    save = { listOf(it.name, it.age) },
    restore = { Person(it[0] as String, it[1] as Int) }
)

@Composable
fun PersonScreen() {
    //实际使用自定义CustomSaver的地方
    var a by remember { mutableStateOf(1) }
    var citySelector by rememberSaveable(stateSaver = CustomSaver) {
        mutableStateOf(Person("ci", 2))
    }
}


//======================================ViewModel=================================
//@Composable函数最好是stateless类型仅用作ui展示, 建立ViewModel来维护其状态及操作等.
@Composable  //此方法仅用作ui展示, 状态及操作都来自外部
fun CounterScreen(count: State<Int>, inc: () -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("the count is ${count.value}", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = inc,
            modifier = Modifier.padding(start = 25.dp, end = 25.dp)
        ) {
            Text("inc")
        }
    }
}

class CounterViewModel : ViewModel() {
    private var _counter = mutableIntStateOf(0)
    val counter = _counter
    val inc = { _counter.intValue += 1 }
}

//使用的地方
fun showCounter() {
    val viewModel: CounterViewModel = viewModel()
    CounterScreen(viewModel.counter, viewModel.inc)
}


//===============================StateHolder=========================
//就是自定义一个类, 将状态及逻辑抽取出来放入其中, 而@Composable方法仅关注ui

//自定义的状态类
class MyStateHolder(
    val snackbarHostState: SnackbarHostState,
    private val resource: Resources,
    showBottomStatue: MutableState<Boolean>
) {

    //定义各种方法, 视图中用
    suspend fun showSnackBar(message: String) {
        snackbarHostState.showSnackbar(message)
    }
}
//此方法用于获取自定义的状态
@Composable
fun rememberMyStateHolder(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    resource: Resources = LocalContext.current.resources!!,
    showBottomStatue: MutableState<Boolean> = rememberSaveable() { mutableStateOf(false) }  //使用rememberSaveable, 则在横竖屏切换等ConfigurationChanged发生时自动恢复
) = remember(snackbarHostState,snackbarHostState) { MyStateHolder(snackbarHostState,resource,showBottomStatue) }
//调用的地方
@Composable
fun MyUI() {
    val mySatte = rememberMyStateHolder()
    //以下视图
}