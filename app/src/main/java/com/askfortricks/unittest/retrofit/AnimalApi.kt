package com.askfortricks.unittest.retrofit

import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.ApiKey
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    //We need key before second api call
    @GET("getKey")
    fun getApiKey():Single<ApiKey>

    //we have to pass key that we get from getApiKey method call
    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key:String?):Single<List<Animal>>

}