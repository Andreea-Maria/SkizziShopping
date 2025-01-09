package com.ainnovations.skizzishoppingapp.viewModels

import com.ainnovations.skizzishoppingapp.data.Product

object CartViewModel {
    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        cartItems.add(product)
    }

    fun removeProduct(product: Product) {
        cartItems.remove(product)
    }

    fun getCartItems(): List<Product> {
        return cartItems
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }
}
