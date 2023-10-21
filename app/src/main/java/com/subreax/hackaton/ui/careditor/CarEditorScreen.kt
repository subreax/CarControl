package com.subreax.hackaton.ui.careditor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.ui.BasePartItem
import com.subreax.hackaton.ui.Title

@Composable
fun CarEditorScreen(
    navBack: () -> Unit,
    carEditorViewModel: CarEditorViewModel = hiltViewModel()
) {
    CarEditorScreen(
        title = carEditorViewModel.title,
        carName = carEditorViewModel.carName,
        onCarNameUpdated = carEditorViewModel::updateCarName,
        parts = carEditorViewModel.parts,
        onPartRemoveClicked = carEditorViewModel::deletePart,
        navBack = navBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditorScreen(
    title: String,
    carName: String,
    onCarNameUpdated: (String) -> Unit,
    parts: List<CarPart>,
    onPartRemoveClicked: (CarPart) -> Unit,
    navBack: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Filled.Add, "Добавить запчасть")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
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
                onPartRemoveClicked = onPartRemoveClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditorScreenContent(
    carName: String,
    onCarNameUpdated: (String) -> Unit,
    parts: List<CarPart>,
    onPartRemoveClicked: (CarPart) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize()) {
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
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, top = 16.dp, end = 16.dp)
            )
        }

        if (parts.isNotEmpty()) {
            items(parts) {
                BasePartItem(
                    name = it.name,
                    typeIconUrl = it.type.iconUrl,
                    health = it.health,
                    trailingIcon = {
                        IconButton(onClick = { onPartRemoveClicked(it) }) {
                            Icon(Icons.Outlined.Delete, "Удалить запчасть")
                        }
                    }
                )
            }
        } else {
            item {
                Text(text = "Пусто", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}

