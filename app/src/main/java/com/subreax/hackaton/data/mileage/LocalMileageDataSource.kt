package com.subreax.hackaton.data.mileage

import android.content.Context
import com.subreax.hackaton.data.Car
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class LocalMileageDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences("mileages", Context.MODE_PRIVATE)

    fun saveMileage(id: UUID, mileage: Float) {
        sharedPrefs.edit()
            .putFloat(id.toString(), mileage)
            .apply()
    }

    fun loadMileage(carId: UUID): Float {
        return sharedPrefs.getFloat(carId.toString(), 0f)
    }
}