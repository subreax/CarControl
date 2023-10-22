package com.subreax.hackaton.ui.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.ServiceUtils
import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.data.mileage.MileageRepository
import com.subreax.hackaton.service.LocationTrackerService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val carRepository: CarRepository,
    private val mileageRepository: MileageRepository
) : ViewModel() {
    var parts by mutableStateOf<List<CarPart>>(emptyList())
        private set

    var carName by mutableStateOf("")
        private set

    var carMileage by mutableFloatStateOf(0f)
        private set

    var carId by mutableStateOf<UUID?>(null)
        private set

    var isButtonActive by mutableStateOf(ServiceUtils.isServiceRunningInForeground(context, LocationTrackerService::class.java))
        private set

    private var cars: List<Car> = emptyList()

    private var mileageJob: Job = Job()

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
        carMileage = car.mileage
        carId = car.id
    }

    fun startMileageTracking() {
        isButtonActive = true
        mileageJob = viewModelScope.launch {
            mileageRepository.trackMileage(cars[0]).collect {
                carMileage = it
            }
        }
    }

    fun stopMileageTracking() {
        isButtonActive = false
        mileageJob.cancel()
    }
}