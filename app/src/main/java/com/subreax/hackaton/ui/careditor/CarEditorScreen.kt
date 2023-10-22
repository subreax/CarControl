package com.subreax.hackaton.ui.careditor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.data.Part2
import com.subreax.hackaton.ui.BasePartItem
import com.subreax.hackaton.ui.Title

private val PartIconSize = 36.dp

@Composable
fun CarEditorScreen(
    navBack: () -> Unit,
    onSave: () -> Unit,
    carEditorViewModel: CarEditorViewModel = hiltViewModel()
) {
    CarEditorScreen(
        title = carEditorViewModel.title,
        carName = carEditorViewModel.carName,
        onCarNameUpdated = carEditorViewModel::updateCarName,
        parts = carEditorViewModel.parts,
        onPartRemoveClicked = carEditorViewModel::deletePart,
        navBack = navBack,
        onSaveClicked = carEditorViewModel::save
    )

    LaunchedEffect(Unit) {
        carEditorViewModel.onSave.collect {
            onSave()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditorScreen(
    title: String,
    carName: String,
    onCarNameUpdated: (String) -> Unit,
    parts: List<Part2>,
    onPartRemoveClicked: (Part2) -> Unit,
    navBack: () -> Unit,
    onSaveClicked: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = navBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Вернуться назад")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            CarEditorScreenContent(
                carName = carName,
                onCarNameUpdated = onCarNameUpdated,
                parts = parts,
                onPartRemoveClicked = onPartRemoveClicked,
                onSaveClicked = onSaveClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditorScreenContent(
    carName: String,
    onCarNameUpdated: (String) -> Unit,
    parts: List<Part2>,
    onPartRemoveClicked: (Part2) -> Unit,
    onSaveClicked: () -> Unit
) {
    Column {
        LazyColumn(Modifier.weight(1f)) {
            item {
                OutlinedTextField(
                    value = carName,
                    onValueChange = onCarNameUpdated,
                    label = { Text("Название") },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Filled.DirectionsCar, contentDescription = "")
                    },
                    maxLines = 1
                )
            }

            item {
                Title(
                    title = "Компоненты",
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    )
                )
            }

            items(parts) {
                BasePartItem(
                    name = it.name,
                    typeIconUrl = it.type.iconUrl,
                    typeIconSize = PartIconSize,
                    health = it.health,
                    trailingIcon = {
                        IconButton(onClick = { onPartRemoveClicked(it) }) {
                            Icon(Icons.Outlined.Delete, "Удалить запчасть")
                        }
                    },
                )
            }

            item {
                AddPartItemButton(onClick = { /*TODO*/}, modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth())
            }
        }

        Button(
            onClick = onSaveClicked, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Сохранить")
        }
    }
}

@Composable
fun AddPartItemButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
    ) {
        Icon(Icons.Filled.Add, "Добавить компонент", modifier = Modifier.size(PartIconSize))
        Text(text = "Добавить компонент")
    }
}

