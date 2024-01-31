package com.example.hw21.presentation.screen.purchase

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw21.databinding.FragmentPurchaseBinding
import com.example.hw21.presentation.adapter.ClothesAdapter
import com.example.hw21.presentation.base.BaseFragment
import com.example.hw21.presentation.base.showSnackBar
import com.example.hw21.presentation.event.ClothesEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseFragment : BaseFragment<FragmentPurchaseBinding>(FragmentPurchaseBinding::inflate) {

    private val recyclerAdapter = ClothesAdapter()
    private val viewModel: PurchaseViewModel by viewModels()

    override fun setupView() {
        with(binding.rvPurchaseItems) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recyclerAdapter
        }
    }

    override fun listeners() {
        recyclerAdapter.onClick = {
            viewModel.onEvent(ClothesEvent.SelectFavouriteEvent(it))
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ClothesState) {
        with(state) {
            clothes?.let {
                recyclerAdapter.submitList(it)
            }
            binding.progressBar.isVisible = isLoading
            binding.root.isRefreshing = isLoading
            errorMessage?.let {
                binding.root.showSnackBar(it)
            }

        }
        binding.root.setOnRefreshListener {
            viewModel.onEvent(ClothesEvent.Refresh)
        }
    }
}