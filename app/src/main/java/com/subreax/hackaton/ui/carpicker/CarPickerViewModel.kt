package com.subreax.hackaton.ui.carpicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.car.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CarPickerViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {
    private var _templates: List<Car> = emptyList()
    private var searchJob: Job = Job()

    var searchValue by mutableStateOf("")
        private set

    var cars = mutableStateListOf<Car>()
        private set

    init {
        viewModelScope.launch {
            val templates = carRepository.getCarTemplates()
            _templates = templates
            cars.addAll(_templates)
        }
    }

    fun updateSearch(search: String) {
        searchValue = search
        searchJob.cancel()

        searchJob = viewModelScope.launch(Dispatchers.Default) {
            val filtered = _templates.filter {
                it.name.startsWith(search, ignoreCase = true)
            }
            withContext(Dispatchers.Main) {
                cars.clear()
                cars.addAll(filtered)
            }
        }
    }
}
