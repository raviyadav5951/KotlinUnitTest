package com.askfortricks.unittest.di

import com.askfortricks.unittest.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

//we have declared as Singleton because PrefsModule was singleton
@Singleton
@Component(modules = [ApiModule::class,PrefsModule::class,AppModule::class])
interface ViewModelComponent {

    //location where we will inject this
    fun inject(viewModel:ListViewModel)
}