package com.example.hw21.presentation.screen.purchase

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw21.R
import com.example.hw21.databinding.FragmentPurchaseBinding
import com.example.hw21.presentation.adapter.CategoryAdapter
import com.example.hw21.presentation.adapter.ClothesAdapter
import com.example.hw21.presentation.base.BaseFragment
import com.example.hw21.presentation.base.showSnackBar
import com.example.hw21.presentation.event.ClothesEvent
import com.example.hw21.presentation.model.FilterItemUiModel
import com.example.hw21.presentation.screen.purchase.FiltersListProvider.categoryList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseFragment : BaseFragment<FragmentPurchaseBinding>(FragmentPurchaseBinding::inflate) {
    private val viewModel: PurchaseViewModel by viewModels()

    private val listRecyclerAdapter = ClothesAdapter()
    private val categoryRvAdapter = CategoryAdapter { category ->
        viewModel.onEvent(ClothesEvent.OnFilterEvent(category))
    }

    override fun setupView() {
        with(binding.rvListItems) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listRecyclerAdapter
        }

        with(binding.rvFilters) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryRvAdapter
            categoryRvAdapter.submitList(categoryList)
        }
    }

    override fun listeners() {
        listRecyclerAdapter.onClick = {
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
                if (filteredClothes != null) {
                    listRecyclerAdapter.submitList(filteredClothes)
                } else {
                    listRecyclerAdapter.submitList(it)
                }
            }
            categories?.let { categoryRvAdapter.submitList(it) }
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

object FiltersListProvider {
    val categoryList = mutableListOf(
        FilterItemUiModel(Category.All, R.color.darkBlue, R.color.txtGray, true),
        FilterItemUiModel(Category.Pardy, R.color.darkBlue, R.color.txtGray),
        FilterItemUiModel(Category.Camping, R.color.darkBlue, R.color.txtGray),
        FilterItemUiModel(Category.Hiking, R.color.darkBlue, R.color.txtGray),
        FilterItemUiModel(Category.Blouse, R.color.darkBlue, R.color.txtGray),
    )
}

enum class Category {
    All,
    Pardy,
    Camping,
    Hiking,
    Blouse,
}