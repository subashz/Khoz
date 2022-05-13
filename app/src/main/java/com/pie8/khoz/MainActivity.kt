package com.pie8.khoz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.pie8.khoz.ui.features.KhozApp
import com.pie8.khoz.ui.utils.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSizeClass = rememberWindowSizeClass()
            KhozApp(windowSizeClass)
        }
    }
}

