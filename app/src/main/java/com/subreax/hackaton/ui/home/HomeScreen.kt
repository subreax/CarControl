package com.subreax.hackaton.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.R
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.BasePartItem
import com.subreax.hackaton.ui.Title
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    locationTracker: LocationTrackerServiceController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var isMileageCounting by remember { mutableStateOf(false) }

    Surface(Modifier.fillMaxSize()) {
        HomeDrawer(
            drawerState = drawerState,
            carNames = homeViewModel.carNames
        ) {
            Column {
                HomeAppBar(
                    title = homeViewModel.carName,
                    onMenuClicked = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    onAccountClicked = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                )

                HomeScreenContent(
                    mileage = 5.25f,
                    isMileageCounting = isMileageCounting,
                    onMileageToggled = { isMileageCounting = it },
                    parts = homeViewModel.parts
                )
            }
        }
    }
}

@Composable
fun HomeAppBar(
    title: String,
    onMenuClicked: () -> Unit,
    onAccountClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = onMenuClicked) {
            Icon(Icons.Filled.Menu, "Меню выбора машины")
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(onClick = onAccountClicked) {
            Icon(Icons.Filled.AccountCircle, "Профиль")
        }
    }
}

@Composable
fun HomeScreenContent(
    mileage: Float,
    isMileageCounting: Boolean,
    onMileageToggled: (Boolean) -> Unit,
    parts: List<CarPart>
) {
    LazyColumn {
        item {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                ToggleMileageTrackerButton(
                    toggled = isMileageCounting,
                    onToggle = onMileageToggled,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 32.dp)
                )

                MileageLabel(
                    mileage = mileage,
                    onEditClicked = { /*TODO*/ }
                )
            }
        }

        item {
            Title(
                title = "Прочность деталей",
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }

        items(parts) {
            BasePartItem(name = it.name, typeIconUrl = it.type.iconUrl, it.health)
        }
    }
}

@Composable
fun MileageLabel(mileage: Float, onEditClicked: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.widthIn(192.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "${mileage.round2()}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "км",
                color = MaterialTheme.colorScheme.outline
            )
        }

        IconButton(onClick = onEditClicked, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(Icons.Filled.Edit, "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDrawer(
    carNames: List<String>,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                carNames.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO */ }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(Icons.Filled.DirectionsCar, "")
                        Text(text = it)
                    }
                }
            }
        },
        drawerState = drawerState,
        content = content
    )
}

@Composable
fun DrawerHeader() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.drawer_bg1),
            contentDescription = "",
            modifier = Modifier
                .aspectRatio(16.0f / 9.0f)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = "Выбор машины",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun ToggleMileageTrackerButton(
    toggled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 168.dp
) {
    val background = if (toggled) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    } else {
        Color.Transparent
    }

    val text = remember(toggled) {
        if (toggled) {
            "Измерение пробега: вкл"
        } else {
            "Измерение пробега: выкл"
        }
    }

    val contentColor = if (toggled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val borderWidth = if (toggled) {
        0.dp
    } else {
        2.dp
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Column(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .clickable(onClick = { onToggle(!toggled) })
                .background(background)
                .border(borderWidth, MaterialTheme.colorScheme.outline, CircleShape),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.LocationSearching, "", modifier = Modifier.size(48.dp))
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


private fun Float.round2(): Float {
    return (this * 100).roundToInt() / 100.0f
}