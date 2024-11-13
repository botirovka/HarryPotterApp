package com.botirovka.harrypotterapp.data.datastore

import android.content.Context

class DataStoreManager(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "harry_potter_prefs"
        private const val KEY_FIRST_LAUNCH = "is_first_launch"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun markFirstLaunchComplete() {
        sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
    }
}