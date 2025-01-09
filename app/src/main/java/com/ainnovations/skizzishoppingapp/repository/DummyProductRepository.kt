package com.ainnovations.skizzishoppingapp.repository

import com.ainnovations.skizzishoppingapp.R
import com.ainnovations.skizzishoppingapp.data.Product

class DummyProductRepository {

    private val allProducts = listOf(
        Product("T-Shirt 1", "T-Shirts", 109.99, R.drawable.produs_1),
        Product("T-Shirt 2", "T-Shirts", 102.50, R.drawable.produs2),
        Product("Skirt 1", "Skirts", 170.00, R.drawable.produs3),
        Product("Pants 1", "Pants", 285.00, R.drawable.produs4),
        Product("Dress 1", "Dresses", 250.00, R.drawable.produs5),

    )

    fun getProductsByCategory(category: String): List<Product> {
        return allProducts.filter { it.category == category }
    }
}
