package com.example.testapp.ui.dogsForBreedList

import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.base.SharedActivityParent
import com.example.testapp.base.SharedFragmentChild
import com.example.testapp.data.network.response.Breed
import com.example.testapp.databinding.FragmentDogsForBreedBinding
import com.example.testapp.tools.setPaddingBottom

class DogsForBreedFragment(override val layoutId: Int = R.layout.fragment_dogs_for_breed) :
    BaseFragment<DogsForBreedViewModel, FragmentDogsForBreedBinding>(), SharedFragmentChild {

    private val breedsAdapter: DogsForBreedAdapter = DogsForBreedAdapter()

    override val sharedParentActivity: SharedActivityParent?
        get() = activity as? SharedActivityParent

    override fun observeViewModel() {
        initReceiver(viewLifecycleOwner)
        viewModel.run {
            dogsForBreedLiveData.observe {
                handleDogsForBreed(it.dogsImages)
                binding.swipeRefreshLayout.isRefreshing = false
            }
            errorLiveData.observe { error ->
                binding.swipeRefreshLayout.isRefreshing = false
                showDefaultErrorDialog(error)
            }
        }
    }

    override fun initViews() {
        binding.run {
            dogsForBreedRv.adapter = breedsAdapter
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.currentBreed?.let(::requestDogsForBreed)
                    ?: kotlin.run { swipeRefreshLayout.isRefreshing = false }
            }
        }
    }

    override fun applyInsetsPadding(
        systemStatusBarSize: Int,
        systemNavigationBarSize: Int,
        isKeyboardOpen: Boolean
    ) {
        binding.toolbar.setPadding(0, systemStatusBarSize, 0, 0)
        binding.dogsForBreedRv.setPaddingBottom(systemNavigationBarSize)
    }

    private fun handleDogsForBreed(list: List<String>) {
        breedsAdapter.setDogsImages(list)
    }

    private fun requestDogsForBreed(breed: Breed) {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.requestDogsForBreed(breed.value)
    }

    override fun sharedDataReceived(data: Any, identifier: String) {
        if (BREED_KEY == identifier && data is Breed) {
            viewModel.currentBreed = data
            binding.toolbar.text = data.value
            requestDogsForBreed(data)
        }
    }

    companion object {
        const val BREED_KEY = "BREED_KEY"
    }
}