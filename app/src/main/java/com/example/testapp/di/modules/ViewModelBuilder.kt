package com.example.testapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.base.SharedViewModel
import com.example.testapp.di.vmfactory.ViewModelFactory
import com.example.testapp.di.vmfactory.ViewModelKey
import com.example.testapp.ui.dogBreedsList.BreedsViewModel
import com.example.testapp.ui.dogsForBreedList.DogsForBreedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelBuilder {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    fun bindSharedViewModel(viewModel: SharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreedsViewModel::class)
    fun bindDogsViewModel(viewModel: BreedsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DogsForBreedViewModel::class)
    fun bindDogsForBreedViewModel(viewModel: DogsForBreedViewModel): ViewModel
}