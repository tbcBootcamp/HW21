package com.example.hw21.presentation.event

import com.example.hw21.presentation.model.ClothesUiModel

sealed class ClothesEvent {
    data object AddAllClotheToDataBaseEvent : ClothesEvent()
    data class SelectFavouriteEvent(val item: ClothesUiModel) : ClothesEvent()
    data object Refresh : ClothesEvent()
}
