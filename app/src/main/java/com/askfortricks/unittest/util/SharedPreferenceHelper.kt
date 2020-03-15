package com.askfortricks.unittest.util

import android.content.Context
import androidx.preference.PreferenceManager

class SharedPreferenceHelper(context: Context) {
    private val PREF_API_KEY = "Api-key"
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveApiKey(key: String) {
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey(): String? = prefs.getString(PREF_API_KEY, null)

}