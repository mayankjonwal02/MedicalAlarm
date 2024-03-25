package com.example.medicalalarm.android.Frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.util.Pair

@Composable
fun BackScreen(composable: @Composable () -> Unit) {
    var colors = listOf(Color.Blue,Color.Magenta.copy(alpha = 0.3f))
    val gradient = Brush.verticalGradient(
        colors = colors,
        startY = 0f,
        endY = 1300f
    )
    Box(modifier = Modifier.fillMaxSize().background(gradient))
    {
        composable()
    }
}