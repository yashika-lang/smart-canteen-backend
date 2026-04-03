package com.example.smartcanteenapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class CartItem(
    val item: MenuItem,
    var quantity: Int
) {
    var qty by mutableStateOf(quantity)
}