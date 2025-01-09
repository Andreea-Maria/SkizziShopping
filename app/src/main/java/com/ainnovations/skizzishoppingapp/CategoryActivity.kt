package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainnovations.skizzishoppingapp.adapters.ProductAdapter
import com.ainnovations.skizzishoppingapp.data.Product
import com.ainnovations.skizzishoppingapp.repository.DummyProductRepository
import com.ainnovations.skizzishoppingapp.viewModels.CartViewModel
import com.google.android.material.tabs.TabLayout

class CategoryActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    // Dummy repository for example
    private val dummyRepository = DummyProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // 1. Change status bar color to match the toolbar (optional)
        //    Make sure you have the color resource in res/values/colors.xml
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)

        // 2. Set up the Toolbar with a back (Up) button
        val toolbar = findViewById<Toolbar>(R.id.toolbarCategory)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Category Page" // or something dynamic

        // 3. Initialize the TabLayout and RecyclerView
        tabLayout = findViewById(R.id.tabLayoutCategories)
        productRecyclerView = findViewById(R.id.productRecyclerView)

        // Setup RecyclerView
        productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter { product ->
            addToCart(product)
        }
        productRecyclerView.adapter = productAdapter

        // 4. Set up the TabLayout
        setupTabs()

        // Check if we got a category from the intent
        val categoryFromIntent = intent.getStringExtra("category")
        // Load the products for that category (or a default)
        loadProducts(categoryFromIntent ?: "T-Shirts")
    }

    // Handle the toolbar Up button (back arrow)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()  // closes this activity, returning to the previous one
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabs() {
        val categories = listOf("T-Shirts", "Skirts", "Pants", "Dresses")
        for (category in categories) {
            val tab = tabLayout.newTab().setText(category)
            tabLayout.addTab(tab)
        }

        // On tab selected -> load the corresponding category
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedCategory = tab?.text.toString()
                loadProducts(selectedCategory)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadProducts(category: String) {
        // Filter the dummy products by category
        val products = dummyRepository.getProductsByCategory(category)
        productAdapter.submitList(products)
    }

    private fun addToCart(product: Product) {
        CartViewModel.addProduct(product)
        Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
    }
}


