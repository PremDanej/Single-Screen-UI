package com.merp.jet.single.ui.screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSpacer(size: Int) {
    Spacer(modifier = Modifier.width(size.dp))
}