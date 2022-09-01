package com.example.testapp.data.network

import arrow.core.Either
import com.example.testapp.data.network.response.BreedsListResponse
import com.example.testapp.data.network.response.DogsForBreedResponse
import com.example.testapp.data.network.response.Error
import retrofit2.http.GET
import retrofit2.http.Path

interface TestApi {
    @GET("breeds/list/all")
    suspend fun getDogBreeds(): Either<Error, BreedsListResponse>

    @GET("breed/{breedName}/images/random/{imageCount}")
    suspend fun getDogsForBreed(
        @Path("breedName") breedName: String,
        @Path("imageCount") imageCount: Int
    ): Either<Error, DogsForBreedResponse>
}
