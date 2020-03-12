package com.askfortricks.unittest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.askfortricks.unittest.model.Animal

class ListViewModel(application: Application):AndroidViewModel(application) {
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh(){

        getAnimals();
    }

    private fun getAnimals(){
        val a1=Animal("Lion")
        val a2=Animal("Cat")
        val a3=Animal("Monkey")
        val a4=Animal("Elephant")
        val a5=Animal("Leopard")
        val a6=Animal("Tiger")

        val animalList= arrayListOf(a1,a2,a3,a4,a5,a6)
        animals.value=animalList
        loading.value=false
        loadError.value=false

    }
}
