package com.askfortricks.unittest.di

import com.askfortricks.unittest.retrofit.AnimalApiService
import dagger.Component
import dagger.Module

@Component(modules = [ApiModule::class])
interface ApiComponent {

    //AnimalApiService determines where this will be injected
    fun inject(service:AnimalApiService)
}