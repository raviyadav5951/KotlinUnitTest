package com.askfortricks.unittest.retrofit

import com.askfortricks.unittest.di.DaggerApiComponent
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.ApiKey
import io.reactivex.Single
import javax.inject.Inject

/**
 * This class can also be termed as Repository in MVVM terminology
 */

class AnimalApiService {

    init {
        DaggerApiComponent.create().inject(this)
    }

    @Inject
    lateinit var api:AnimalApi

    fun getApiKey():Single<ApiKey> = api.getApiKey()

    fun getAnimals(key:String?):Single<List<Animal>> = api.getAnimals(key)

}