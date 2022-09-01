package com.example.testapp.ui.dogBreedsList

import arrow.core.Either
import com.example.testapp.data.DogRepository
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.response.Breed
import com.example.testapp.data.network.response.Error
import javax.inject.Inject

class BreedsUseCase @Inject constructor(private val testRepository: DogRepository) {

    suspend fun getBreeds(loadCallback: suspend ((Either<Error, DataWrapper<List<Breed>>>) -> Unit)) {
        testRepository.requestDogBreedsList(loadCallback)
    }
}