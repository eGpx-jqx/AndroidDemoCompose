package com.example.androiddemo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AndroidDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
      SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
      }
    }

    /**
     * staticCompositionLocal及CompositionLocal都会创建当前节点及子节点可用全局变量，但是staticCompositionLocal是其数据变化则内部会全部重组，  CompositionLocal则是数据发生变更则仅使用到其值的子节点会发生重组
     * 1：在这里传递自定义的 颜色，字体，形状给到MaterialTheme方法
     * -》 此方法将remember缓存传递的对象（如colorScheme）
     * -》通过CompositionLocalProvider的方式将被缓存的对象赋值给对应的localxxx当前节点全局对象（staticCompositionLocal或CompositionLocal类型）
     * 2：MaterialTheme.colorScheme.background这种访问将访问到当前root节点的localxxx数据，也就是传输-缓存-最后赋值给localxxx的变量，  如 colorScheme
     * 3：2的访问方式 MaterialTheme存在一个单例，实际 访问到了localxxx
     */
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}