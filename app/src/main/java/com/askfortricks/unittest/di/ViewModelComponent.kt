package com.askfortricks.unittest.di

import com.askfortricks.unittest.viewmodel.ListViewModel
import dagger.Component
import dagger.Module

@Component(modules = [ApiModule::class])
interface ViewModelComponent {

    //location where we will inject this
    fun inject(viewModel:ListViewModel)
}