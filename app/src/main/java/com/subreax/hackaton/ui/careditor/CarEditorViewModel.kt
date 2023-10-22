package com.subreax.hackaton.ui.careditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.Part2
import com.subreax.hackaton.data.car.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CarEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val carRepository: CarRepository
) : ViewModel() {
    private var _parts = mutableStateListOf<Part2>()
    val parts: List<Part2> = _parts

    var carName by mutableStateOf("")
        private set

    var title by mutableStateOf("")
        private set

    private val _onSave = MutableSharedFlow<Boolean>()
    val onSave: SharedFlow<Boolean> = _onSave

    init {
        val carId = savedStateHandle.get<String>("carId")
        if (!carId.isNullOrEmpty()) {
            title = "Редактирование машины"

            viewModelScope.launch {
                val car = carRepository.getCarById(UUID.fromString(carId))!!
                carName = car.name
                _parts.clear()
                _parts.addAll(car.parts)
            }
        }
        else {
            title = "Сборка машины"
        }
    }

    fun updateCarName(newName: String) {
        carName = newName
    }

    fun deletePart(part: Part2) {
        _parts.removeIf { it.id == part.id }
    }

    fun save() {
        viewModelScope.launch {
            _onSave.emit(true)
        }
    }
}