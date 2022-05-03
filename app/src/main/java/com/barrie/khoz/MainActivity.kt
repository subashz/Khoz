package com.barrie.khoz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.barrie.khoz.ui.features.KhozApp
import com.barrie.khoz.ui.features.splash.SplashScreen
import com.barrie.khoz.ui.utils.rememberWindowSizeClass

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

