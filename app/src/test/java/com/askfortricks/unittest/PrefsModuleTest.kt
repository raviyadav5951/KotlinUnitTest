package com.askfortricks.unittest

import android.app.Application
import com.askfortricks.unittest.di.PrefsModule
import com.askfortricks.unittest.util.SharedPreferenceHelper

class PrefsModuleTest(val mockPrefs:SharedPreferenceHelper): PrefsModule() {

    override fun provideSharedPreferences(app: Application): SharedPreferenceHelper {
        return mockPrefs
    }
}