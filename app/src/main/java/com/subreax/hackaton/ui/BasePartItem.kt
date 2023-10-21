package com.subreax.hackaton.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.subreax.hackaton.ui.theme.HackatonTheme
import java.net.URL
import kotlin.math.roundToInt

@Composable
fun BasePartItem(
    name: String,
    typeIcon: @Composable () -> Unit,
    health: Float,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        typeIcon()

        Column(Modifier.weight(1f)) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "${(health * 100).roundToInt()}%")
            }
            LinearProgressIndicator(
                progress = health,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }

        trailingIcon()
    }
}

@Composable
fun BasePartItem(
    name: String,
    typeIconUrl: URL?,
    health: Float,
    modifier: Modifier = Modifier,
    typeIconSize: Dp = 36.dp,
    trailingIcon: @Composable () -> Unit = {}
) {
    BasePartItem(
        name = name,
        typeIcon = {
            if (typeIconUrl != null) {
                AsyncImage(
                    model = typeIconUrl.toString(),
                    contentDescription = "",
                    modifier = Modifier.size(typeIconSize)
                )
            } else {
                Icon(Icons.Filled.Construction, "", modifier = Modifier.size(typeIconSize))
            }
        },
        health = health,
        modifier = modifier,
        trailingIcon = trailingIcon
    )
}


@Preview(widthDp = 400)
@Composable
fun CarPartItemPreview() {
    HackatonTheme {
        /*CarPartItem(part = CarPart(
            id = UUID.randomUUID(),
            name = "Деталька",
            type = CarPartGroup(
                id = UUID.randomUUID(),
                name = "Амортизатор",
                description = "Делает движение плавнее",
                iconUrl = URL("data:image/png;base64,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")
            ),
            mileage = 200,
            mileageLeft = 150,
            installationDate = Date(),
            expiresAt = Date(System.currentTimeMillis() + 10000)
        ))*/
        Surface {
            BasePartItem(
                name = "Деталька",
                typeIconUrl = null,
                health = 0.9f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}