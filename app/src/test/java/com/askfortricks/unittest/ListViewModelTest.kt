package com.askfortricks.unittest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.askfortricks.unittest.di.AppModule
import com.askfortricks.unittest.di.DaggerViewModelComponent
import com.askfortricks.unittest.retrofit.AnimalApiService
import com.askfortricks.unittest.util.SharedPreferenceHelper
import com.askfortricks.unittest.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListViewModelTest {

    @get:Rule
    var rule=InstantTaskExecutorRule()
    @Mock
    lateinit var prefs:SharedPreferenceHelper

    @Mock
    lateinit var animalService: AnimalApiService

    val application= Mockito.mock(Application::class.java)

    var listViewModel=ListViewModel(application,true)

    @Before
    fun setUpRxSchedulers(){
        val immediate=object: Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({it.run()},true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler {scheduler->immediate  }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate  }
    }


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)

    }


}