package com.example.androiddemo.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * 自定义全局变量， 如bgImage
 */
open class CustomExtraValue private constructor(
    var bgImage: Int
) {
    object dark_bgImage : CustomExtraValue(
        bgImage = 1 //(R.drawable.xxx)
    )
    object light_bgImage : CustomExtraValue(
        bgImage = 1 //(R.drawable.xxx)
    )

    //创建一个本地的节点全局静态变量， 在MaterialTheme的扩展方法处引用
    internal var localBGImage = staticCompositionLocalOf {
        CustomExtraValue.light_bgImage as CustomExtraValue //默认亮
    }
}