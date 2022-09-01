package test.java.com.example.testapp.ui.dogsBreedList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.example.testapp.data.DogRepository
import com.example.testapp.data.network.DataWrapper
import com.example.testapp.data.network.response.Breed
import com.example.testapp.data.network.response.Error
import com.example.testapp.ui.dogBreedsList.BreedsUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class BreedsViewModelTest {

    val getBreedsUseCase = mock<BreedsUseCase>()

    @Before
    fun before(){

    }

    @BeforeEach
    fun beforeEach(){

    }

    @AfterEach
    fun afterEach(){
        Mockito.reset(getBreedsUseCase)
    }

    @Test
    fun `should load breeds`() = runBlocking {

    }

    @Test
    fun `should load dogs for breed`() = runBlocking {

    }
}