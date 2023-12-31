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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

data class UiCarTemplate(
    val id: UUID,
    val name: String,
    val type: Car.Type,
)

@HiltViewModel
class CarPickerViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {
    private var _templates: List<UiCarTemplate> = emptyList()
    private var searchJob: Job = Job()

    var searchValue by mutableStateOf("")
        private set

    var cars = mutableStateListOf<UiCarTemplate>()
        private set

    private val _navHome = MutableSharedFlow<UUID>()
    val navHome: Flow<UUID> = _navHome

    init {
        viewModelScope.launch {
            val templates = carRepository.getCarTemplates()
            _templates = templates.map { UiCarTemplate(it.id, it.name, it.type) }
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

    fun createCar(template: UiCarTemplate) {
        viewModelScope.launch {
            val car = carRepository.createCarFromTemplateById(template.id)
            _navHome.emit(car.id)
        }
    }
}
