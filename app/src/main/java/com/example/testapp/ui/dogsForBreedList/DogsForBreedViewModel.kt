package com.example.testapp.ui.dogsForBreedList

import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.SingleLiveEvent
import com.example.testapp.data.network.response.Breed
import com.example.testapp.data.network.response.DogsForBreedResponse
import javax.inject.Inject

class DogsForBreedViewModel @Inject constructor(
    private val getDogsForBreedUseCase: DogsForBreedUseCase
) : BaseViewModel() {

    var currentBreed: Breed? = null

    val dogsForBreedLiveData: SingleLiveEvent<DogsForBreedResponse> =
        SingleLiveEvent()

    fun requestDogsForBreed(breed: String) {
        launchCoroutineScope {
            getDogsForBreedUseCase
                .getDogsForBreed(breed)
                .fold(ifLeft = { error ->
                    errorLiveData.postValue(error)
                }, ifRight = { dataResponseWrapper ->
                    dogsForBreedLiveData.postValue(dataResponseWrapper)
                })
        }
    }

}