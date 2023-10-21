package com.subreax.hackaton.ui.carpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.HelpCenter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.data.Car

private val carItemModifier = Modifier
    .padding(16.dp)
    .fillMaxWidth()

@Composable
fun CarPickerScreen(
    showButtonBack: Boolean,
    navBack: () -> Unit,
    navToCarEditor: (Car) -> Unit,
    navToCarBuilder: () -> Unit,
    carPickerViewModel: CarPickerViewModel = hiltViewModel()
) {
    val cars = carPickerViewModel.cars

    CarPickerScreen(
        cars = cars,
        search = carPickerViewModel.searchValue,
        onSearchUpdated = carPickerViewModel::updateSearch,
        showButtonBack = showButtonBack,
        navBack = navBack,
        onCarPicked = navToCarEditor,
        onCreateNewCar = navToCarBuilder
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarPickerScreen(
    cars: List<Car>,
    search: String,
    onSearchUpdated: (String) -> Unit,
    showButtonBack: Boolean,
    navBack: () -> Unit,
    onCarPicked: (Car) -> Unit,
    onCreateNewCar: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text("Создание новой машины")
                },
                navigationIcon = {
                    if (showButtonBack) {
                        IconButton(onClick = navBack) {
                            Icon(Icons.Filled.ArrowBack, "Вернуться назад")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Создайте машину, за которой будет осуществляться автоматизированный контроль",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            OutlinedTextField(
                value = search,
                onValueChange = onSearchUpdated,
                label = {
                    Text(text = "Поиск")
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, "Поиск")
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                maxLines = 1
            )

            if (cars.isNotEmpty()) {
                LazyColumn(Modifier.weight(1f)) {
                    items(cars) {
                        CarItem(it, onCarPicked, modifier = carItemModifier)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Пусто", modifier = Modifier.align(Alignment.Center))
                }
            }

            ThereIsNoCarButton(onCreateNewCar, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ThereIsNoCarButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Outlined.HelpCenter, "В списке нет моей машины")
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Вашей машины нет в списке?",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Соберите её сами!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Icon(Icons.Filled.ChevronRight, "")
        }
    }
}

@Composable
fun CarItem(car: Car, onClick: (Car) -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .clickable(onClick = { onClick(car) })
            .then(modifier)
    ) {
        val icon = if (car.type == Car.Type.Passenger)
            Icons.Filled.DirectionsCar
        else
            Icons.Filled.LocalShipping

        Icon(icon, contentDescription = "")
        Text(text = car.name)
    }
}