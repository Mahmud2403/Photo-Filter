package com.example.photofilter.ui.screens.components

import android.view.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.photofilter.ui.theme.PhotoFilterTheme
import com.example.photofilter.ui.theme.StatusBarConfig
import com.example.photofilter.ui.theme.isSystemInDarkThemeCustom


@Composable
fun Root(window: Window, content: @Composable () -> Unit) {
    val isDark = isSystemInDarkThemeCustom()
    PhotoFilterTheme(isDark) {
        window.StatusBarConfig(isDark)
        Surface(color = MaterialTheme.colorScheme.background) {
            content.invoke()
        }
    }
}