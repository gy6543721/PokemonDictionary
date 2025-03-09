package levilin.pokemon.dictionary.utility

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.verticalScrollbar(
    state: LazyListState,
    width: Float = 12f,
    barTint: Color = Color.White,
    backgroundColor: Color = Color.Gray,
): Modifier {
    return drawWithContent {
        drawContent()

        val totalItems = state.layoutInfo.totalItemsCount
        val visibleItems = state.layoutInfo.visibleItemsInfo.size

        if (totalItems > visibleItems) {
            val firstVisibleItemIndex = state.firstVisibleItemIndex
            val scrollProgress = firstVisibleItemIndex.toFloat() / (totalItems - visibleItems)

            val scrollbarHeight = this.size.height * (visibleItems.toFloat() / totalItems)
            val offsetY = (this.size.height - scrollbarHeight) * scrollProgress

            // ScrollBar Background
            drawRoundRect(
                color = backgroundColor,
                topLeft = Offset(x = this.size.width - width, y = 0f),
                size = Size(width, this.size.height),
                cornerRadius = CornerRadius(width / 2, width / 2),
                alpha = 0.5f
            )

            // ScrollBar
            drawRoundRect(
                color = barTint,
                topLeft = Offset(x = this.size.width - width, y = offsetY),
                size = Size(width, scrollbarHeight),
                cornerRadius = CornerRadius(width / 2, width / 2),
                alpha = 1f
            )
        }
    }
}