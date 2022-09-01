package com.example.testapp.di.modules.builders

import com.example.testapp.ui.dogBreedsList.BreedsFragment
import com.example.testapp.ui.dogsForBreedList.DogsForBreedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeBreedsFragment(): BreedsFragment

    @ContributesAndroidInjector
    fun contributeDogsForBreedFragment(): DogsForBreedFragment

}