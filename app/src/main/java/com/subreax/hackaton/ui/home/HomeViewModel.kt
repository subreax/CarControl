package com.subreax.hackaton.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.data.car.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {
    var parts by mutableStateOf<List<CarPart>>(emptyList())
        private set

    var carName by mutableStateOf("")
        private set

    private var cars: List<Car> = emptyList()

    private val _carNames = mutableStateListOf<String>()
    val carNames: List<String> = _carNames

    init {
        viewModelScope.launch {
            cars = carRepository.getCars()
            selectCar(0)
            _carNames.clear()
            _carNames.addAll(cars.map { it.name })
        }
    }

    private fun selectCar(idx: Int) {
        val car = cars[idx]
        carName = car.name
        parts = car.parts
    }
}