package com.example.app_ff.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.app_ff.ui.utils.obtenerWindowSizeClass


@Composable
fun HomeScreen2(){
    val windowSizeClass = obtenerWindowSizeClass()
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()

    }
}