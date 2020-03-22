package com.askfortricks.unittest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.askfortricks.unittest.di.AppModule
import com.askfortricks.unittest.di.CONTEXT_APP
import com.askfortricks.unittest.di.DaggerViewModelComponent
import com.askfortricks.unittest.di.TypeOfContext
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.ApiKey
import com.askfortricks.unittest.retrofit.AnimalApiService
import com.askfortricks.unittest.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// We have taken injected var to know if viewmodel called from reallife scenarion or from test class.
//If ListViewModel called from test then injected=true and we dont inject DaggerComponent since its a test and we have mock.

class ListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application,test:Boolean=true):this(application){
        injected=true
    }

    private var injected=false

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    //create disposable and release it later in onCleared
    private val disposable = CompositeDisposable()

    //create shared pref instance //See PrefModule
    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var  prefs : SharedPreferenceHelper
    //create api service
    @Inject
    lateinit var api: AnimalApiService

    fun inject(){
        if(!injected){
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    /*init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }*/

    fun refresh() {
        inject()
        loading.value=true
        if(prefs.getApiKey().isNullOrEmpty()){
            getKey()
        }
        else{
            getAnimals(prefs.getApiKey())
        }
    }

    //called from swipe refresh
    fun forceRefresh(){
        inject()
        loading.value=true
        getKey()
    }
    //first call this method to call api
    fun getKey() {
        disposable.add(
            api.getApiKey().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(keyObject: ApiKey) {
                        if(keyObject.key.isNullOrEmpty()){
                            loadError.value=true
                            loading.value=false
                        }
                        else{
                            prefs.saveApiKey(keyObject.key)
                            loadError.value=false
                            loading.value=false
                            getAnimals(keyObject.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value=true
                        loading.value=false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun getAnimals(apiKey: String?) {
        disposable.add(
            api.getAnimals(apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(animalList: List<Animal>) {
                        if(animalList.isEmpty()){
                            loadError.value=true
                            loading.value=false
                        }
                        else{
                            loadError.value=false
                            loading.value=false
                            animals.value=animalList
                        }
                    }

                    override fun onError(e: Throwable) {
                        loading.value=false
                        loadError.value=true
                        animals.value=null
                        e.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
