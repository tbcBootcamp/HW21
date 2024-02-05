package com.example.hw21.presentation.screen.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw21.R
import com.example.hw21.data.global.common.Resource
import com.example.hw21.domain.usecase.AddUserToDataBaseUseCase
import com.example.hw21.domain.usecase.GetClothesFromApiUseCase
import com.example.hw21.domain.usecase.GetClothesFromDataBaseUseCase
import com.example.hw21.presentation.event.ClothesEvent
import com.example.hw21.presentation.mapper.toDomain
import com.example.hw21.presentation.mapper.toPresentation
import com.example.hw21.presentation.model.ClothesUiModel
import com.example.hw21.presentation.model.FilterItemUiModel
import com.example.hw21.presentation.screen.purchase.FiltersListProvider.categoryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val getClothesFromApiUseCase: GetClothesFromApiUseCase,
    getClothesFromDataBaseUseCase: GetClothesFromDataBaseUseCase,
    private val addUserUseCase: AddUserToDataBaseUseCase
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow(ClothesState())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    private val _clothesFlow = getClothesFromDataBaseUseCase()

    init {
        getUsers()
        getCategories()
    }

    private fun getCategories() {
        _usersStateFlow.update { it.copy(categories = categoryList) }
    }

    fun onEvent(event: ClothesEvent) {
        when (event) {
            is ClothesEvent.AddAllClotheToDataBaseEvent -> addAllClothes()
            is ClothesEvent.SelectFavouriteEvent -> selectFavourite(selectedClothes = event.item)
            is ClothesEvent.Refresh -> getUsers()
            is ClothesEvent.OnFilterEvent -> onFilterEvent(event.category)
        }
    }

    private fun onFilterEvent(category: FilterItemUiModel) {
        val categories = usersStateFlow.value.categories?.map {
            it.copy(isActive = category == it)
        }

        val list = usersStateFlow.value.clothes?.filter { cloth ->
            if (category.filterName != Category.All) {
                cloth.category == category.filterName.name
            } else true
        }
        _usersStateFlow.update { it.copy(filteredClothes = list, categories = categories) }
    }

    private fun getUsers() {
        viewModelScope.launch {
            getClothesFromApiUseCase().collect {
                when (it) {
                    is Resource.Loading -> _usersStateFlow.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _usersStateFlow.update { currentState -> currentState.copy(clothes = it.response.map { domainModel -> domainModel.toPresentation() }) }
                        addAllClothes()
                    }

                    is Resource.Error -> {
                        if (it.throwable is IOException) {
                            _clothesFlow.collect { clothes ->
                                if (clothes.isNotEmpty())
                                    _usersStateFlow.update { currentState ->
                                        currentState.copy(
                                            clothes = clothes.map { clothesDomainModel -> clothesDomainModel.toPresentation() },
                                            isLoading = false
                                        )
                                    }
                                else
                                    _usersStateFlow.update { clothesState ->
                                        clothesState.copy(
                                            errorMessage = R.string.no_items_found.toString(),
                                            isLoading = false
                                        )
                                    }

                            }
                        } else {
                            _usersStateFlow.update { currentState -> currentState.copy(errorMessage = it.message) }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun addAllClothes() {
        viewModelScope.launch {
            addUserUseCase(_usersStateFlow.value.clothes!!.map { it.toDomain() })
        }
    }

    private fun addSingleClothes(selectedClothes: ClothesUiModel) {
        viewModelScope.launch {
            addUserUseCase(listOf(selectedClothes.toDomain()))
        }
    }

    private fun selectFavourite(selectedClothes: ClothesUiModel) {
        viewModelScope.launch {
            val modifiedClothesList = _usersStateFlow.value.clothes?.map { clothes ->
                if (clothes.id == selectedClothes.id) {
                    clothes.copy(favorite = !selectedClothes.favorite)
                } else {
                    clothes
                }
            }
            val modifiedFilteredClothesList = _usersStateFlow.value.filteredClothes?.map { clothes ->
                if (clothes.id == selectedClothes.id) {
                    clothes.copy(favorite = !selectedClothes.favorite)
                } else {
                    clothes
                }
            }
            _usersStateFlow.update { currentState ->
                currentState.copy(
                    clothes = modifiedClothesList,
                    filteredClothes = modifiedFilteredClothesList
                )
            }
            addSingleClothes(selectedClothes.copy(favorite = !selectedClothes.favorite))
        }
    }
}

data class ClothesState(
    val isLoading: Boolean = false,
    val clothes: List<ClothesUiModel>? = null,
    val filteredClothes: List<ClothesUiModel>? = null,
    val errorMessage: String? = null,
    val categories: List<FilterItemUiModel>? = null
)