package com.askfortricks.unittest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.askfortricks.unittest.di.AppModule
import com.askfortricks.unittest.di.DaggerViewModelComponent
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.ApiKey
import com.askfortricks.unittest.retrofit.AnimalApiService
import com.askfortricks.unittest.util.SharedPreferenceHelper
import com.askfortricks.unittest.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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


    var key="Any key"

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

    @Test
    fun getAnimalSuccess(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal=Animal("Cow",null,null,null,null,null,null)
        val animalList= listOf(animal)
        val testSingle=Single.just(animalList)

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.animals.value?.size)
        Assert.assertEquals(false,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }

    @Test
    fun getAnimalFailure(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val testSingle=Single.error<List<Animal>>(Throwable())
        val keySingle=Single.just(ApiKey("ok",key))

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()
        Assert.assertEquals(null,listViewModel.animals.value)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(true,listViewModel.loadError.value)
    }


    //Adding api key test
    //For key success check ListViewModel
    //Design test based on the original method call.
    //Like here at first we have null in pref so we start with returning null.
    //We have to test two flows . one is key success and second is getAnimal should be also success.
    @Test
    fun getApiKeySuccess(){
        //Flow 1 for validating key
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val apiKey=ApiKey("ok",key)
        val keySingle=Single.just(apiKey)

        //Flow 2 api key is success and we need to call getAnimals using that valid key.
        //Now we should successfully get
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)
        val animal=Animal("Cow",null,null,null,null,null,null)
        val animalList= listOf(animal)
        val testSingle=Single.just(animalList)

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.animals.value)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(false,listViewModel.loadError.value)
    }


    @Test
    fun getKeyFailure(){
        //Flow 1 for validating key
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle=Single.error<ApiKey>(Throwable())
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null,listViewModel.animals.value)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(true,listViewModel.loadError.value)

    }

}