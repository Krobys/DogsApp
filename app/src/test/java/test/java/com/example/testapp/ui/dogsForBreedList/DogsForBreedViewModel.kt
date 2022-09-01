package test.java.com.example.testapp.ui.dogsForBreedList

import arrow.core.Either
import com.example.testapp.data.network.response.DogsForBreedResponse
import com.example.testapp.data.network.response.Error
import com.example.testapp.ui.dogsForBreedList.DogsForBreedUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class DogsForBreedViewModel {

    private val getDogsForBreedUseCase = mock<DogsForBreedUseCase>()

    private val SUCCESS_STATUS = "success"

    private val dogsImagesList1 = listOf(
        "image1.png",
        "image2.png",
        "image3.png",
        "image4.png"
    )

    private val dogsImagesList2 = listOf(
        "image5.png",
        "image6.png",
        "image7.png",
        "image8.png"
    )

    @BeforeEach
    fun beforeEach() {

    }

    @AfterEach
    fun afterEach() {
        Mockito.reset(getDogsForBreedUseCase)
    }

    @Test
    fun `should load dogs successful for breed`() = runBlocking {
        val breed = "malamute"
        val result: Either<Error, DogsForBreedResponse> = Either.Right(
            DogsForBreedResponse(dogsImagesList1, SUCCESS_STATUS)
        )
        Mockito.`when`(getDogsForBreedUseCase.getDogsForBreed(breed)).thenReturn(result)
        val testResult = getDogsForBreedUseCase.getDogsForBreed(breed)
        testResult.fold(ifLeft = {
            assert(false)
        }, ifRight = {
            assert(it.dogsImages.isNotEmpty())
            assert(it.dogsImages == dogsImagesList1)
            Assertions.assertEquals(it.status, SUCCESS_STATUS)
        })
    }

    @Test
    fun `should load dogs successful for breed not matching previous breed`() = runBlocking {
        val breed = "malamute"
        val result: Either<Error, DogsForBreedResponse> = Either.Right(
            DogsForBreedResponse(dogsImagesList1, SUCCESS_STATUS)
        )
        Mockito.`when`(getDogsForBreedUseCase.getDogsForBreed(breed)).thenReturn(result)
        val testResult = getDogsForBreedUseCase.getDogsForBreed(breed)
        testResult.fold(ifLeft = {
            assert(false)
        }, ifRight = {
            assert(it.dogsImages.isNotEmpty())
            assert(it.dogsImages != dogsImagesList2)
            Assertions.assertEquals(it.status, SUCCESS_STATUS)
        })
    }

    @Test
    fun `should load with error for empty breed`() = runBlocking {
        val noRouteFoundErrorMessage = "No route found for \\\"GET \\/api\\/breed\\/\\/images\\/random\\/20\\"
        val breed = ""
        val result: Either<Error, DogsForBreedResponse> = Either.Left(
            Error(404, noRouteFoundErrorMessage)
        )
        Mockito.`when`(getDogsForBreedUseCase.getDogsForBreed(breed)).thenReturn(result)
        val testResult = getDogsForBreedUseCase.getDogsForBreed(breed)
        testResult.fold(ifLeft = {
            Assertions.assertEquals(it.code, 404)
            Assertions.assertEquals(it.message, noRouteFoundErrorMessage)
        }, ifRight = {
            assert(false)
        })

    }

    @Test
    fun `should load with error for wrong breed`() = runBlocking {
        val wrongBreedErrorMessage = "Breed not found (master breed does not exist)"
        val breed = "WrongBreedHere"
        val result: Either<Error, DogsForBreedResponse> = Either.Left(
            Error(404, wrongBreedErrorMessage)
        )
        Mockito.`when`(getDogsForBreedUseCase.getDogsForBreed(breed)).thenReturn(result)
        val testResult = getDogsForBreedUseCase.getDogsForBreed(breed)
        testResult.fold(ifLeft = {
            Assertions.assertEquals(it.code, 404)
            Assertions.assertEquals(it.message, wrongBreedErrorMessage)
        }, ifRight = {
            assert(false)
        })
    }



}