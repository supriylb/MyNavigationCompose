package com.example.mynavigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mynavigationcompose.ui.AppShell
import com.example.mynavigationcompose.ui.theme.MyNavigationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyNavigationComposeTheme {
                AppShell()
            }
        }
    }
}
