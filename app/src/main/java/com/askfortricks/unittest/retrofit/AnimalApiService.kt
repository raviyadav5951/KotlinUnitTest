package com.askfortricks.unittest.retrofit

import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.ApiKey
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class can also be termed as Repository in MVVM terminology
 */

class AnimalApiService {
    private val BASE_URL="https://us-central1-apis-4674e.cloudfunctions.net"

    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey():Single<ApiKey> = api.getApiKey()

    fun getAnimals(key:String?):Single<List<Animal>> = api.getAnimals(key)

}