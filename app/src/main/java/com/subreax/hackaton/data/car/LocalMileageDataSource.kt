package com.subreax.hackaton.data.car

import android.content.Context
import com.subreax.hackaton.data.Car
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalMileageDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences("mileages", Context.MODE_PRIVATE)

    fun saveMileage(car: Car) {
        sharedPrefs.edit()
            .putFloat(car.id.toString(), car.mileage)
            .apply()
    }

    fun loadMileage(car: Car): Float {
        return sharedPrefs.getFloat(car.id.toString(), 0f)
    }
}