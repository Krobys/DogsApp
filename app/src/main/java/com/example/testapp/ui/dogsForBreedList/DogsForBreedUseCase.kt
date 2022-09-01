package com.example.testapp.ui.dogsForBreedList

import com.example.testapp.data.DogRepository
import javax.inject.Inject

class DogsForBreedUseCase @Inject constructor(private val testRepository: DogRepository) {

    suspend fun getDogsForBreed(breed: String) = testRepository.requestDogsForBreed(breed)

}