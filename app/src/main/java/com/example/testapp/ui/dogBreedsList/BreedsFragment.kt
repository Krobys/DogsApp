package com.example.testapp.ui.dogBreedsList

import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.base.SharedActivityParent
import com.example.testapp.base.SharedFragmentChild
import com.example.testapp.data.network.response.Breed
import com.example.testapp.databinding.FragmentBreedsBinding
import com.example.testapp.tools.asScreenAnimated
import com.example.testapp.tools.setPaddingBottom
import com.example.testapp.ui.dogsForBreedList.DogsForBreedFragment
import com.example.testapp.ui.dogsForBreedList.DogsForBreedFragment.Companion.BREED_KEY

class BreedsFragment(override val layoutId: Int = R.layout.fragment_breeds) :
    BaseFragment<BreedsViewModel, FragmentBreedsBinding>(), SharedFragmentChild {

    private val breedsAdapter: BreedsAdapter = BreedsAdapter(::onBreedClick)

    override val sharedParentActivity: SharedActivityParent?
        get() = activity as? SharedActivityParent

    override fun observeViewModel() {
        viewModel.run {
            breedsLiveData.observe {
                handleBreeds(it.data)
                if (it.isFromRemote) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            errorLiveData.observe { error ->
                binding.swipeRefreshLayout.isRefreshing = false
                showDefaultErrorDialog(error)
            }
        }
    }

    override fun initViews() {
        requestBreeds()
        binding.run {
            breedsRv.adapter = breedsAdapter
            swipeRefreshLayout.setOnRefreshListener {
                requestBreeds()
            }
        }
    }

    override fun applyInsetsPadding(
        systemStatusBarSize: Int,
        systemNavigationBarSize: Int,
        isKeyboardOpen: Boolean
    ) {
        binding.toolbar.setPadding(0, systemStatusBarSize, 0, 0)
        binding.breedsRv.setPaddingBottom(systemNavigationBarSize)
    }

    private fun handleBreeds(list: List<Breed>) {
        breedsAdapter.setBreeds(list)
    }

    private fun requestBreeds() {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.requestBreeds()
    }

    private fun onBreedClick(breed: Breed){
        sendDataToReceiver(DogsForBreedFragment::class.java, breed, BREED_KEY)
        router.navigateTo(DogsForBreedFragment::class.java.asScreenAnimated(TransitionAnimation.HORISONTAL))
    }
}