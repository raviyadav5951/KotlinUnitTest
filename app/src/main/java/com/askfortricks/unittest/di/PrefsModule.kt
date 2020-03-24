package com.askfortricks.unittest.di

import android.app.Application
import com.askfortricks.unittest.util.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Assume practical scenario here that SharedPreferenceHelper needed application context.
 * Now our same method can be called from activity then it needs activity context.
 * So we have the facility of Qualifier given by dagger.
 *
 * Steps we had followed are:
 * Defined two const val Strings CONTEXT_APP,CONTEXT_ACTIVITY
 * Defined Qualifier
 * Included @TypeOfContext annotation to each method to differentiate between them.
 * Check ListViewModel class we have used:
 * @field:TypeOfContext(CONTEXT_APP) to inject provideSharedPreferences with application context.
 */


@Module
open class PrefsModule {

    //creating single instance to avoid multiple instances changing the state before accessing in large scale app.
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun provideSharedPreferences(app: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    open fun provideActivitySharedPreferences(app: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }
}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Activity Context"

@Qualifier
annotation class TypeOfContext(val type: String)

