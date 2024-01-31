package com.example.hw21.presentation.screen.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw21.R
import com.example.hw21.data.global.common.Resource
import com.example.hw21.domain.usecase.AddUserToDataBaseUseCase
import com.example.hw21.domain.usecase.GetClothesFromApiUseCase
import com.example.hw21.domain.usecase.GetClothesFromDataBaseUseCase
import com.example.hw21.presentation.event.ClothesEvent
import com.example.hw21.presentation.model.ClothesUiModel
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
    }

    fun onEvent(event: ClothesEvent) {
        when(event) {
            is ClothesEvent.AddAllClotheToDataBaseEvent -> addAllClothes()
            is ClothesEvent.SelectFavouriteEvent -> selectFavourite(selectedClothes = event.item)
            ClothesEvent.Refresh -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            getClothesFromApiUseCase().collect {
                when(it) {
                    is Resource.Loading -> _usersStateFlow.update { currentState -> currentState.copy(isLoading = it.loading) }

                    is Resource.Success -> {
                        _usersStateFlow.update { currentState -> currentState.copy(clothes = it.response) }
                        addAllClothes()
                    }

                    is Resource.Error -> {
                        if (it.throwable is IOException){
                            _clothesFlow.collect {clothes ->
                                if (clothes.isNotEmpty())
                                    _usersStateFlow.update { currentState -> currentState.copy(clothes = clothes, isLoading = false) }
                                else
                                    _usersStateFlow.update { clothesState -> clothesState.copy(errorMessage = R.string.no_items_found.toString(), isLoading = false) }

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
            addUserUseCase(_usersStateFlow.value.clothes!!)
        }
    }

    private fun addSingleClothes(selectedClothes: ClothesUiModel) {
        viewModelScope.launch {
            addUserUseCase(listOf(selectedClothes))
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
            _usersStateFlow.update { currentState -> currentState.copy(clothes = modifiedClothesList) }
            addSingleClothes(selectedClothes.copy(favorite = !selectedClothes.favorite))
        }
    }
}

data class ClothesState(
    val isLoading: Boolean = false,
    val clothes: List<ClothesUiModel>? = null,
    val errorMessage: String? = null
)