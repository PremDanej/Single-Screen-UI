 package com.merp.jet.single.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.merp.jet.single.ui.screen.R

@Composable
fun AppLogo(
    image: Painter = painterResource(id = R.drawable.ic_launcher),
    size: Int = 120,
    shape: CornerBasedShape = MaterialTheme.shapes.medium
) {
    Image(
        painter = image,
        contentDescription = "App Logo",
        modifier = Modifier
            .size(size.dp)
            .padding(8.dp)
            .clip(shape)
    )
}