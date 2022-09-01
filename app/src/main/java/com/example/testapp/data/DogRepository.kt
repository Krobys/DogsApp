package com.example.testapp.data

import arrow.core.Either
import com.example.testapp.data.database.TestDatabase
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.TestApi
import com.example.testapp.data.network.response.Breed
import com.example.testapp.data.network.response.Error
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val testNetwork: TestApi,
    private val testDatabase: TestDatabase
) {

    suspend fun requestDogBreedsList(onLoadCallback: suspend ((Either<Error, DataWrapper<List<Breed>>>) -> Unit)) {
        repositoryTreatment(onLoadCallback = onLoadCallback,
            loadLocal = {
                return@repositoryTreatment it.breedDao().getBreeds()
            }, loadRemote = {
                return@repositoryTreatment it.getDogBreeds()
                    .map { breedsRawResponse -> breedsRawResponse.getBreeds() }
            }, onCacheFromRemote = { data, database ->
                database.breedDao().run {
                    clearTable()
                    setBreeds(breeds = data)
                }
            })
    }

    suspend fun requestDogsForBreed(
        breed: String,
        count: Int = 20
    ) = testNetwork.getDogsForBreed(breed, count)

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private suspend fun <T> repositoryTreatment(
        loadRemote: suspend ((testNetwork: TestApi) -> Either<Error, T>),
        loadLocal: suspend ((TestDatabase: TestDatabase) -> T?),
        onCacheFromRemote: suspend ((T, TestDatabase: TestDatabase) -> Unit),
        onLoadCallback: suspend (Either<Error, DataWrapper<T>>) -> Unit
    ) {
        val localData = loadLocal.invoke(testDatabase)
        localData?.let {
            onLoadCallback.invoke(wrapLocalData(localData, false))
        }


        val remoteData = loadRemote.invoke(testNetwork)

        remoteData.fold(ifLeft = {
            val remoteDataWrapper = wrapRemoteData(remoteData)
            onLoadCallback.invoke(remoteDataWrapper)
        }, ifRight = {
            onCacheFromRemote.invoke(it, testDatabase)

            loadLocal.invoke(testDatabase)?.let {
                onLoadCallback.invoke(wrapLocalData(it, true))
            }

        })


    }

    private fun <T> wrapLocalData(data: T, isFromRemote: Boolean): Either<Error, DataWrapper<T>> {
        return Either.Right(DataWrapper(isFromRemote, data))
    }

    private fun <T> wrapRemoteData(data: Either<Error, T>): Either<Error, DataWrapper<T>> {
        return data.fold(ifLeft = {
            Either.Left(it)
        }, ifRight = {
            Either.Right(DataWrapper(isFromRemote = true, data = it))
        })
    }
}