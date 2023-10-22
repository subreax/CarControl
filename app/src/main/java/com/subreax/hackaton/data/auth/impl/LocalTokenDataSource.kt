package com.subreax.hackaton.data.auth.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalTokenDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPrefs.edit()
            .putString("token", token)
            .apply()
    }

    fun loadToken(): String? {
        return sharedPrefs.getString("token", null)
    }
}