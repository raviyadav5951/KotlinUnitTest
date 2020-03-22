package com.askfortricks.unittest

import com.askfortricks.unittest.di.ApiModule
import com.askfortricks.unittest.retrofit.AnimalApiService

class ApiModuleTest (val mockApiModule:AnimalApiService) :ApiModule(){
    override fun provideAnimalApiService(): AnimalApiService {
        return mockApiModule
    }
}