package com.example.testapp.ui.dogBreedsList

import androidx.lifecycle.viewModelScope
import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.SingleLiveEvent
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.response.Breed
import javax.inject.Inject

class BreedsViewModel @Inject constructor(
    private val loadBreedsUseCase: BreedsUseCase
) : BaseViewModel() {

    val breedsLiveData: SingleLiveEvent<DataWrapper<List<Breed>>> =
        SingleLiveEvent()

    fun requestBreeds() {
        launchCoroutineScope {
            loadBreedsUseCase.getBreeds {
                it.fold(ifLeft = { error ->
                    errorLiveData.postValue(error)
                }, ifRight = { dataResponseWrapper ->
                    breedsLiveData.postValue(dataResponseWrapper)
                })
            }
        }
    }
}